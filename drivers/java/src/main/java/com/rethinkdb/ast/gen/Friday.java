// Autogenerated by metajava.py.
// Do not edit this file directly.
// The template for this file is located at:
// ../../../../../../../../templates/AstSubclass.java
package com.rethinkdb.ast.gen;

import com.rethinkdb.ast.helper.Arguments;
import com.rethinkdb.ast.helper.OptArgs;
import com.rethinkdb.ast.RqlAst;
import com.rethinkdb.proto.TermType;
import java.util.*;



public class Friday extends RqlQuery {


    public Friday(java.lang.Object arg) {
        this(new Arguments(arg), null);
    }
    public Friday(Arguments args, OptArgs optargs) {
        this(null, args, optargs);
    }
    public Friday(RqlAst prev, Arguments args, OptArgs optargs) {
        this(prev, TermType.FRIDAY, args, optargs);
    }
    protected Friday(RqlAst previous, TermType termType, Arguments args, OptArgs optargs){
        super(previous, termType, args, optargs);
    }


    /* Static factories */
    public static Friday fromArgs(Object... args){
        return new Friday(new Arguments(args), null);
    }


}
