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



public class Desc extends RqlQuery {


    public Desc(java.lang.Object arg) {
        this(new Arguments(arg), null);
    }
    public Desc(Arguments args, OptArgs optargs) {
        this(null, args, optargs);
    }
    public Desc(RqlAst prev, Arguments args, OptArgs optargs) {
        this(prev, TermType.DESC, args, optargs);
    }
    protected Desc(RqlAst previous, TermType termType, Arguments args, OptArgs optargs){
        super(previous, termType, args, optargs);
    }


    /* Static factories */
    public static Desc fromArgs(Object... args){
        return new Desc(new Arguments(args), null);
    }


}
