package de.therealkings.patches.pou

import app.revanced.patcher.patch.bytecodePatch
import app.revanced.patcher.fingerprint

val pouBannerAdFingerprint = fingerprint {
    strings("ca-app-pub-1078512594475745/8910428114")
}

@Patch(name = "Remove Pou banner ads")
object RemovePouAdsPatch : BytecodePatch(
    setOf(pouBannerAdFingerprint)
) {
    override fun execute(context: BytecodeContext) {
        val method = pouBannerAdFingerprint.method
        val addViewIndex = method.instructions.indexOfFirst {
            it.opcode == Opcode.INVOKE_VIRTUAL &&
            (it as? ReferenceInstruction)?.reference.toString()
                .contains("Landroid/view/ViewGroup;->addView(Landroid/view/View;)V")
        }
        if (addViewIndex >= 0) method.removeInstruction(addViewIndex)
    }
}