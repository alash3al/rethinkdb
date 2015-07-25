package com.rethinkdb.net;

import com.rethinkdb.proto.TermType;
import com.rethinkdb.proto.Version;
import com.rethinkdb.proto.QueryType;
import com.rethinkdb.proto.Protocol;
import com.rethinkdb.ast.Query;
import com.rethinkdb.ast.helper.OptArgs;
import com.rethinkdb.model.GlobalOptions;
import com.rethinkdb.response.Response;
import com.rethinkdb.response.DBResultFactory;
import com.rethinkdb.ast.RqlAst;
import com.rethinkdb.ast.Util;
import com.rethinkdb.ast.gen.Db;
import com.rethinkdb.ReqlDriverError;
import com.rethinkdb.RethinkDBConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;
import java.nio.ByteBuffer;
import java.util.*;
import java.lang.InstantiationException;
import java.lang.reflect.InvocationTargetException;

public class Connection<C extends ConnectionInstance> {
    // public immutable
    public final String hostname;
    public final int port;

    // private immutable
    private final String authKey;
    private final AtomicLong nextToken = new AtomicLong();
    private final Class<C> connType;

    // private mutable
    private Optional<String> dbname;
    private Optional<Integer> connectTimeout;
    private ByteBuffer handshake;
    private Optional<C> instance = Optional.empty();

    public Connection(Class<C> connType,
                      Optional<String> hostname,
                      Optional<Integer> port,
                      Optional<String> db,
                      Optional<String> authKey,
                      Optional<Integer> timeout
                      // TODO: ssl
                      ) {
        this.dbname = db;
        this.authKey = authKey.orElse("");
        this.handshake = Util.leByteBuffer(4 + 4 + this.authKey.length() + 4)
            .putInt(Version.V0_4.value)
            .putInt(this.authKey.length())
            .put(this.authKey.getBytes())
            .putInt(Protocol.JSON.value);
        this.hostname = hostname.orElse("localhost");
        this.port = port.orElse(28015);
        this.connectTimeout = timeout;

        this.connType = connType;
    }

    public Optional<String> db() {
        return dbname;
    }

    public void use(String db) {
        dbname = Optional.of(db);
    }

    public Optional<Integer> timeout() {
        return connectTimeout;
    }

    public Connection reconnect(boolean noreplyWait, Optional<Integer> timeout) {
        if(!timeout.isPresent()){
            timeout = connectTimeout;
        }
        close(noreplyWait);
        try {
            C inst = connType
                .getDeclaredConstructor(Connection.class)
                .newInstance(this);
            instance = Optional.of(inst);
            return inst.connect(timeout);
        } catch (InstantiationException
                 |NoSuchMethodException
                 |IllegalAccessException
                 |InvocationTargetException ex) {
            throw new ReqlDriverError(ex);
        }
    }

    public boolean isOpen() {
        return instance.map(c -> c.isOpen()).orElse(false);
    }

    public void checkOpen() {
        if(instance.map(c -> !c.isOpen()).orElse(true)){
            throw new ReqlDriverError("Connection is closed.");
        }
    }

    public void close(boolean noreplyWait) {
        instance.ifPresent(inst -> {
            long noreplyToken = newToken();
            instance = Optional.empty();
            nextToken.set(0);
            inst.close(noreplyWait, noreplyToken);
        });
    }

    public Optional<Object> noreplyWait() {
        checkOpen();
        Query q = new Query(QueryType.NOREPLY_WAIT, newToken());
        // already checked if the instance is open, so just using .get()
        return instance.get().runQuery(q, false);
    }

    private long newToken() {
        return nextToken.incrementAndGet();
    }

    // optional<Object> start(RqlQuery term, GlobalOptions globalOpts) {
    //     checkOpen();
    //     dbname.ifPresent(db -> {
    //         globalOpts.db()
    //     });
    // }
    //TODO: create factory methods for DefaultConnection etc.
}
