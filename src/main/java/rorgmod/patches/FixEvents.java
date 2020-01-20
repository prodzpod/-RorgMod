package rorgmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.events.exordium.Mushrooms;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.Instanceof;
import rorgmod.RorgMod;

@SpirePatch(clz = ProceedButton.class, method = "update")
public class FixEvents { // from vexmod
    // Note: this should really be moved to BaseMod
    public static ExprEditor Instrument() {
        return new ExprEditor() {
            @Override
            public void edit(Instanceof i) throws CannotCompileException {
                try {
                    if (i.getType().getName().equals(Mushrooms.class.getName())) {
                        RorgMod.logger.info("Finding and gremlin wheel event proceed button");
                        i.replace("$_ = $proceed($$) || currentRoom.event instanceof rorgmod.events.GremlinWheel;");
                    }
                } catch (NotFoundException e) {
                    RorgMod.logger.error("Combat proceed button patch broken.", e);
                }
            }
        };
    }
}
