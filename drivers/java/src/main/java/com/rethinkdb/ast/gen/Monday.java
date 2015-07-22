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



public class Monday extends RqlQuery {


    public Monday(java.lang.Object arg) {
        this(new Arguments(arg), null);
    }
    public Monday(Arguments args, OptArgs optargs) {
        this(null, args, optargs);
    }
    public Monday(RqlAst prev, Arguments args, OptArgs optargs) {
        this(prev, TermType.MONDAY, args, optargs);
    }
    protected Monday(RqlAst previous, TermType termType, Arguments args, OptArgs optargs){
        super(previous, termType, args, optargs);
    }


    /* Static factories */
    public static Monday fromArgs(Object... args){
        return new Monday(new Arguments(args), null);
    }


}
