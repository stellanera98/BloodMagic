package wayoftime.bloodmagic.datagen.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.common.fluid.BMFluids;
import wayoftime.bloodmagic.common.item.BMItems;
import wayoftime.bloodmagic.util.DemonWillType;
import wayoftime.bloodmagic.util.helper.BlockWithItemHolder;

public class BMLanguageProvider extends LanguageProvider {
    public BMLanguageProvider(PackOutput output, String locale) {
        super(output, BloodMagic.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + BloodMagic.MODID + ".main", "Blood Magic");
        add("itemGroup." + BloodMagic.MODID + ".upgrades", "Blood Magic Upgrade Tomes");
        add("itemGroup." + BloodMagic.MODID + ".anointments", "Blood Magic Anointments");

        add(BMFluids.LIFE_ESSENCE_TYPE.get(), "Life Essence");
        add(BMFluids.LIFE_ESSENCE_BUCKET.get(), "Life Essence Bucket");
        add(BMFluids.LIFE_ESSENCE_BLOCK.get(), "Life Essence");

        add(BMFluids.DOUBT_TYPE.get(), "Liquid Doubt");
        add(BMFluids.DOUBT_BUCKET.get(), "Doubt Bucket");
        add(BMFluids.DOUBT_BLOCK.get(), "Liquid Doubt");

        add(BMBlocks.BLOOD_TANK, "Blood Tank");
        addTooltip("container_tier", "Tier: %d");
        addTooltip("container_tier_missing", "Tier missing!");
        addTooltip("fluid_content", "%s: %d");
        addTooltip("fluid_content_empty", "Empty");

        addTooltip("save_for_decoration", "Save for decoration");
        add(BMBlocks.BLOODSTONE, "Bloodstone");
        add(BMBlocks.BLOODSTONE_BRICK, "Bloodstone Brick");

        add(BMBlocks.CRYSTAL_CLUSTER, "Crystal Cluster");
        add(BMBlocks.CRYSTAL_BRICK, "Crystal Cluster Brick");

        add(BMBlocks.BLOOD_ALTAR, "Blood Altar");

        add(BMBlocks.RUNE_BLANK, "Blank Rune");

        add(BMBlocks.RUNE_SACRIFICE, "Rune of Sacrifice");
        add(BMBlocks.RUNE_SELF_SACRIFICE, "Rune of Self Sacrifice");
        add(BMBlocks.RUNE_CAPACITY, "Rune of Capacity");
        add(BMBlocks.RUNE_CAPACITY_AUGMENTED, "Rune of Augmented Capacity");
        add(BMBlocks.RUNE_DISLOCATION, "Rune of Dislocation");
        add(BMBlocks.RUNE_ORB, "Rune of the Orb");
        add(BMBlocks.RUNE_CHARGING, "Charging Rune");
        add(BMBlocks.RUNE_SPEED, "Speed Rune");
        add(BMBlocks.RUNE_ACCELERATION, "Acceleration Rune");
        add(BMBlocks.RUNE_EFFICIENCY, "Efficiency Rune");

        add(BMBlocks.RUNE_2_SACRIFICE, "Reinforced Rune of Sacrifice");
        add(BMBlocks.RUNE_2_SELF_SACRIFICE, "Reinforced Rune of Self Sacrifice");
        add(BMBlocks.RUNE_2_CAPACITY, "Reinforced Rune of Capacity");
        add(BMBlocks.RUNE_2_CAPACITY_AUGMENTED, "Reinforced Rune of Augmented Capacity");
        add(BMBlocks.RUNE_2_DISLOCATION, "Reinforced Rune of Dislocation");
        add(BMBlocks.RUNE_2_ORB, "Reinforced Rune of the Orb");
        add(BMBlocks.RUNE_2_CHARGING, "Reinforced Charging Rune");
        add(BMBlocks.RUNE_2_SPEED, "Reinforced Speed Rune");
        add(BMBlocks.RUNE_2_ACCELERATION, "Reinforced Acceleration Rune");
        add(BMBlocks.RUNE_2_EFFICIENCY, "Reinforced Efficiency Rune");

        add(BMBlocks.HELLFIRE_FORGE, "Hellfire Forge");
        add(BMItems.RAW_WILL.get(), "Raw Will");

        add(BMItems.SOUL_GEM_PETTY.get(), "Petty Tartaric Gem");
        add(BMItems.SOUL_GEM_LESSER.get(), "Lesser Tartaric Gem");
        add(BMItems.SOUL_GEM_COMMON.get(), "Common Tartaric Gem");
        add(BMItems.SOUL_GEM_GREATER.get(), "Greater Tartaric Gem");
        add(BMItems.SOUL_GEM_GRAND.get(), "Grand Tartaric Gem");
        addGemDesc(BMItems.SOUL_GEM_PETTY, "a little");
        addGemDesc(BMItems.SOUL_GEM_LESSER, "some");
        addGemDesc(BMItems.SOUL_GEM_COMMON, "more");
        addGemDesc(BMItems.SOUL_GEM_GREATER, "a greater amount of");
        addGemDesc(BMItems.SOUL_GEM_GRAND, "a large amount of");

        addTooltip("will", "Will Quality: %s");
        for (DemonWillType type : DemonWillType.values()) {
            addTooltip("current_type." + type.toLower(), String.format("Contains: %s Will", type.toCapitalized()));
        }

        addTooltip("currentOwner", "Current Owner: %s");
        addTooltip("noOwner", "Not Bound Yet");
        add(BMItems.ORB_WEAK.get(), "Weak Blood Orb");
        add(BMItems.ORB_APPRENTICE.get(), "Apprentice Blood Orb");
        add(BMItems.ORB_MAGICIAN.get(), "Mage Blood Orb");
        add(BMItems.ORB_MASTER.get(), "Master Blood Orb");
        add(BMItems.ORB_ARCHMAGE.get(), "Archmage Blood Orb");
        add(BMItems.ORB_TRANSCENDENT.get(), "Transcendent Blood Orb");

        add(BMItems.SACRIFICIAL_DAGGER.get(), "Sacrificial Dagger");
    }

    public void add(FluidType type, String name) {
        add(type.getDescriptionId(), name);
    }

    public void addGemDesc(DeferredHolder holder, String desc) {
        addTooltip("soul_gem." + holder.getId().getPath(), String.format("A gem used to contain %s will.", desc));
    }

    public void add(BlockWithItemHolder<? extends Block, ? extends BlockItem> block, String name) {
        add(block.block().get().getDescriptionId(), name);
    }

    public void addTooltip(String name, String value) {
        add("tooltip.bloodmagic." + name, value);
    }
}
