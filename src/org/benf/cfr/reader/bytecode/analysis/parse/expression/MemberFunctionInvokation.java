package org.benf.cfr.reader.bytecode.analysis.parse.expression;

import org.benf.cfr.reader.bytecode.analysis.parse.Expression;
import org.benf.cfr.reader.bytecode.analysis.parse.utils.LValueCollector;
import org.benf.cfr.reader.entities.ConstantPool;
import org.benf.cfr.reader.entities.ConstantPoolEntry;
import org.benf.cfr.reader.entities.ConstantPoolEntryMethodRef;
import org.benf.cfr.reader.entities.ConstantPoolEntryNameAndType;
import org.benf.cfr.reader.util.ListFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: lee
 * Date: 16/03/2012
 * Time: 17:26
 * To change this template use File | Settings | File Templates.
 */
public class MemberFunctionInvokation implements Expression {
    private final ConstantPoolEntryMethodRef function;
    private Expression object;
    private final List<Expression> args;
    private final ConstantPool cp;
    
    public MemberFunctionInvokation(ConstantPool cp, ConstantPoolEntryMethodRef function, Expression object, List<Expression> args) {
        this.function = function;
        this.object = object;
        this.args = ListFactory.newList();
        // idiot, there has to be a standard way....
        for (int x=args.size()-1;x>=0;--x) this.args.add(args.get(x));
        this.cp = cp;
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public Expression replaceSingleUsageLValues(LValueCollector lValueCollector) {
        object = object.replaceSingleUsageLValues(lValueCollector);
        for (int x=0;x<args.size();++x) {
            args.set(x, args.get(x).replaceSingleUsageLValues(lValueCollector));
        }
        return this;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(object.toString());
        sb.append(".");
        ConstantPoolEntryNameAndType nameAndType = cp.getNameAndTypeEntry(function.getNameAndTypeIndex());
        sb.append(nameAndType.getName(cp).getValue());
        sb.append("(");
        boolean first = true;
        for (Expression arg : args) {
            if (!first) sb.append(", ");
            first = false;
            sb.append(arg.toString());
        }
        sb.append(")");
        return sb.toString();
    }

    public Expression getObject() {
        return object;
    }

    public ConstantPoolEntryMethodRef getFunction() {
        return function;
    }

    public List<Expression> getArgs() {
        return args;
    }

    public ConstantPool getCp() {
        return cp;
    }
}
