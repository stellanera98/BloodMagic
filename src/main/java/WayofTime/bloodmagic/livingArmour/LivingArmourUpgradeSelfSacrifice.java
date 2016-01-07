package WayofTime.bloodmagic.livingArmour;

import net.minecraft.nbt.NBTTagCompound;
import WayofTime.bloodmagic.api.Constants;
import WayofTime.bloodmagic.api.livingArmour.LivingArmourUpgrade;

public class LivingArmourUpgradeSelfSacrifice extends LivingArmourUpgrade
{
    //TODO: Add extra effects for higher levels
    public static final int[] costs = new int[] { 7, 13, 22, 40, 65, 90, 130, 180, 250, 350 };
    public static final double[] sacrificeModifier = new double[] { 0.15, 0.3, 0.45, 0.6, 0.75, 0.9, 1.05, 1.2, 1.35, 1.5 };

    public LivingArmourUpgradeSelfSacrifice(int level)
    {
        super(level);
    }

    public double getSacrificeModifier()
    {
        return sacrificeModifier[this.level];
    }

    @Override
    public String getUniqueIdentifier()
    {
        return Constants.Mod.MODID + ".upgrade.selfSacrifice";
    }

    @Override
    public int getMaxTier()
    {
        return 10; // Set to here until I can add more upgrades to it.
    }

    @Override
    public int getCostOfUpgrade()
    {
        return costs[this.level];
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        // EMPTY
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        // EMPTY
    }

    @Override
    public String getUnlocalizedName()
    {
        return tooltipBase + "selfSacrifice";
    }
}