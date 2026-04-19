package wayoftime.bloodmagic.api.capability;

import wayoftime.bloodmagic.common.datacomponent.EnumWillType;

public interface IWillHandler {

    double fill(EnumWillType type, double max, boolean doFill);

    double drain(EnumWillType type, double max, boolean doDrain);
}
