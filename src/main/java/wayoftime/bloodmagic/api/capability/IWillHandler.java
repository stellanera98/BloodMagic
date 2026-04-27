package wayoftime.bloodmagic.api.capability;

import wayoftime.bloodmagic.common.datacomponent.EnumWillType;
import wayoftime.bloodmagic.common.datamap.WillStack;

public interface IWillHandler {

    WillStack getWillStack();

    double fill(EnumWillType type, double max, boolean doFill);

    double drain(EnumWillType type, double max, boolean doDrain);
}
