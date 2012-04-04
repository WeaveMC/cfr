package org.benf.cfr.reader.bytecode.analysis.parse.utils;

import org.benf.cfr.reader.entities.ConstantPool;
import org.benf.cfr.reader.entities.attributes.LocalVariableEntry;
import org.benf.cfr.reader.util.output.Dumper;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * User: lee
 * Date: 02/04/2012
 */
public class VariableNamerHinted implements VariableNamer {
    private final TreeSet<LocalVariableEntry> localVariableEntryTreeSet = new TreeSet<LocalVariableEntry>(new OrderLocalVariables());
    private final ConstantPool cp;    
    
    public VariableNamerHinted(List<LocalVariableEntry> entryList, ConstantPool cp) {
        localVariableEntryTreeSet.addAll(entryList);
        this.cp = cp;     
    }

    @Override
    public String getName(int originalRawOffset, long stackPosition) {
        originalRawOffset += 2;
        LocalVariableEntry tmp = new LocalVariableEntry((short)(originalRawOffset), (short)1, (short)-1, (short)-1, (short)stackPosition);
        LocalVariableEntry lve = localVariableEntryTreeSet.floor(tmp);

        if (lve.getIndex() == stackPosition && 
            lve.getStartPc() <= (originalRawOffset) &&
            (lve.getStartPc() + lve.getLength()) >= originalRawOffset) {
            return cp.getUTF8Entry(lve.getNameIndex()).getValue();
        } else {
            Dumper d = new Dumper();
            this.dump(d, cp);
            System.out.println("Looking up variable at " + originalRawOffset + ", index " + stackPosition);
            String lveName = cp.getUTF8Entry(lve.getNameIndex()).getValue();
            return "badlocal_" + lveName + "_" + stackPosition;
        }
    }

    private void dump(Dumper d, ConstantPool cp) {
        for (LocalVariableEntry lve : localVariableEntryTreeSet) {
            lve.dump(d, cp);
        }
    }

    private static class OrderLocalVariables implements Comparator<LocalVariableEntry> {
        @Override
        public int compare(LocalVariableEntry a, LocalVariableEntry b) {
            int x = a.getIndex() - b.getIndex();
            if (x != 0) return x;
            return a.getStartPc() - b.getStartPc();
        }
    }
}
