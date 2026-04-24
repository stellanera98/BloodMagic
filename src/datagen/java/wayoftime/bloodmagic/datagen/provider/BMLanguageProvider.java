package wayoftime.bloodmagic.datagen.provider;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.api.BMIdentifiers;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;
import wayoftime.bloodmagic.common.fluid.BMFluids;
import wayoftime.bloodmagic.common.item.BMItems;
import wayoftime.bloodmagic.datagen.content.LivingUpgrades;
import wayoftime.bloodmagic.datagen.content.SigilData;
import wayoftime.bloodmagic.util.blockitem.BlockWithItemHolder;

import static wayoftime.bloodmagic.common.datacomponent.EnumWillType.*;

public class BMLanguageProvider extends LanguageProvider {

    public BMLanguageProvider(PackOutput output) {
        super(output, BloodMagic.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        //Fluids - Life Essence
        add(BMFluids.LIFE_ESSENCE_TYPE.get().getDescriptionId(), "Life Essence");
        add(BMFluids.LIFE_ESSENCE_BUCKET.get(), "Bucket of Life");
        add(BMFluids.LIFE_ESSENCE_BLOCK.get(), "Life Essence");

        //Fluids - Liquid Doubt
        add(BMFluids.DOUBT_TYPE.get().getDescriptionId(), "Liquid Doubt");
        add(BMFluids.DOUBT_BUCKET.get(), "Doubt Bucket");
        add(BMFluids.DOUBT_BLOCK.get(), "Liquid Doubt");

        //Orbs
        add(BMItems.ORB_WEAK.get(), "Weak Blood Orb");
        add(BMItems.ORB_APPRENTICE.get(), "Apprentice Blood Orb");
        add(BMItems.ORB_MAGICIAN.get(), "Magician Blood Orb");
        add(BMItems.ORB_MASTER.get(), "Master Blood Orb");
        add(BMItems.ORB_ARCHMAGE.get(), "Archmage Blood Orb");
        add(BMItems.ORB_TRANSCENDENT.get(), "Transcendent Blood Orb");

        //Binding Info
        addTooltip("current_owner", "Current Owner: %s");
        addTooltip("no_owner", "Not bound yet");

        add(BMBlocks.BLOOD_ALTAR, "Blood Altar");
        add(BMItems.SACRIFICIAL_DAGGER.get(), "Sacrificial Dagger");

        //Runes - Blank
        add(BMBlocks.RUNE_BLANK, "Blank Rune");

        //Runes - Basic
        add(BMBlocks.RUNE_SACRIFICE, "Rune of Sacrifice");
        add(BMBlocks.RUNE_SELF_SACRIFICE, "Rune of Self Sacrifice");
        add(BMBlocks.RUNE_SPEED, "Speed Rune");
        add(BMBlocks.RUNE_ACCELERATION, "Acceleration Rune");
        add(BMBlocks.RUNE_DISLOCATION, "Displacement Rune");
        add(BMBlocks.RUNE_CAPACITY, "Capacity Rune");
        add(BMBlocks.RUNE_CAPACITY_AUGMENTED, "Augmented Capacity Rune");
        add(BMBlocks.RUNE_CHARGING, "Charging Rune");
        add(BMBlocks.RUNE_ORB, "Rune of the Orb");
        add(BMBlocks.RUNE_EFFICIENCY, "Rune of Efficiency");

        //Runes - Reinforced
        add(BMBlocks.RUNE_2_SACRIFICE, "Reinforced Rune of Sacrifice");
        add(BMBlocks.RUNE_2_SELF_SACRIFICE, "Reinforced Rune of Self Sacrifice");
        add(BMBlocks.RUNE_2_SPEED, "Reinforced Speed Rune");
        add(BMBlocks.RUNE_2_ACCELERATION, "Reinforced Acceleration Rune");
        add(BMBlocks.RUNE_2_DISLOCATION, "Reinforced Displacement Rune");
        add(BMBlocks.RUNE_2_CAPACITY, "Reinforced Capacity Rune");
        add(BMBlocks.RUNE_2_CAPACITY_AUGMENTED, "Reinforced Augmented Capacity Rune");
        add(BMBlocks.RUNE_2_CHARGING, "Reinforced Charging Rune");
        add(BMBlocks.RUNE_2_ORB, "Reinforced Rune of the Orb");
        add(BMBlocks.RUNE_2_EFFICIENCY, "Reinforced Rune of Efficiency");

        add(BMBlocks.BLOODSTONE, "Polished Bloodstone");
        add(BMBlocks.BLOODSTONE_BRICK, "Bloodstone Brick");

        add(BMBlocks.HELLFORGED_BLOCK, "Hellforged Block");

        add(BMBlocks.CRYSTAL_CLUSTER, "Crystal Cluster");
        add(BMBlocks.CRYSTAL_CLUSTER_BRICK, "Crystal Cluster Brick");

        addTooltip("safe_for_decoration", "Safe for Decoration");

        add(BMBlocks.ARC_BLOCK, "Alchemical Reaction Chamber");

        add(BMBlocks.BLOOD_TANK, "Blood Tank");
        addTooltip("container_tier_missing", "No Tier found!");
        addTooltip("container_tier", "Current Tier: %s");
        addTooltip("fluid_content_empty", "Empty");
        addTooltip("fluid_content", "Contains: %smB of %s");

        add(BMBlocks.IMPERFECT_RITUAL_BLOCK, "Imperfect Ritual Stone");

        add(BMBlocks.HELLFIRE_FORGE, "Hellfire Forge");
        add(BMBlocks.WILL_CRUCIBLE, "Demon Crucible");
        add(BMBlocks.WILL_INFUSER, "Demon Will Infuser");

        // Crystal Belljars
        add(BMBlocks.BELLJAR_OAK, "Oak Will Jar");
        add(BMBlocks.BELLJAR_SPRUCE, "Spruce Will Jar");
        add(BMBlocks.BELLJAR_BIRCH, "Birch Will Jar");
        add(BMBlocks.BELLJAR_CHERRY, "Cherry Will Jar");
        add(BMBlocks.BELLJAR_JUNGLE, "Jungle Will Jar");
        add(BMBlocks.BELLJAR_DARK_OAK, "Dark Oak Will Jar");
        add(BMBlocks.BELLJAR_CRIMSON, "Crimson Will Jar");
        add(BMBlocks.BELLJAR_WARPED, "Warped Will Jar");
        add(BMBlocks.BELLJAR_MANGROVE, "Mangrove Will Jar");
        add(BMBlocks.BELLJAR_BAMBOO, "Bamboo Will Jar");

        // Will Blocks
        add(BMBlocks.WILL_BLOCK_RAW, "Block of Raw Will");
        add(BMBlocks.WILL_BLOCK_CORROSIVE, "Block of Corrosive Will");
        add(BMBlocks.WILL_BLOCK_DESTRUCTIVE, "Block of Destructive Will");
        add(BMBlocks.WILL_BLOCK_STEADFAST, "Block of Steadfast Will");
        add(BMBlocks.WILL_BLOCK_VENGEFUL, "Block of Vengeful Will");

        // Budding Will Blocks
        add(BMBlocks.BUDDING_WILL_RAW, "Budding Raw Will");
        add(BMBlocks.BUDDING_WILL_CORROSIVE, "Budding Corrosive Will");
        add(BMBlocks.BUDDING_WILL_DESTRUCTIVE, "Budding Destructive Will");
        add(BMBlocks.BUDDING_WILL_STEADFAST, "Budding Steadfast Will");
        add(BMBlocks.BUDDING_WILL_VENGEFUL, "Budding Vengeful Will");

        // Small Will Buds
        add(BMBlocks.WILL_BUD_SMALL_RAW, "Small Raw Will Bud");
        add(BMBlocks.WILL_BUD_SMALL_CORROSIVE, "Small Corrosive Will Bud");
        add(BMBlocks.WILL_BUD_SMALL_DESTRUCTIVE, "Small Destructive Will Bud");
        add(BMBlocks.WILL_BUD_SMALL_STEADFAST, "Small Steadfast Will Bud");
        add(BMBlocks.WILL_BUD_SMALL_VENGEFUL, "Small Vengeful Will Bud");

        // Medium Will Buds
        add(BMBlocks.WILL_BUD_MEDIUM_RAW, "Medium Raw Will Bud");
        add(BMBlocks.WILL_BUD_MEDIUM_CORROSIVE, "Medium Corrosive Will Bud");
        add(BMBlocks.WILL_BUD_MEDIUM_DESTRUCTIVE, "Medium Destructive Will Bud");
        add(BMBlocks.WILL_BUD_MEDIUM_STEADFAST, "Medium Steadfast Will Bud");
        add(BMBlocks.WILL_BUD_MEDIUM_VENGEFUL, "Medium Vengeful Will Bud");

        // Large Will Buds
        add(BMBlocks.WILL_BUD_LARGE_RAW, "Large Raw Will Bud");
        add(BMBlocks.WILL_BUD_LARGE_CORROSIVE, "Large Corrosive Will Bud");
        add(BMBlocks.WILL_BUD_LARGE_DESTRUCTIVE, "Large Destructive Will Bud");
        add(BMBlocks.WILL_BUD_LARGE_STEADFAST, "Large Steadfast Will Bud");
        add(BMBlocks.WILL_BUD_LARGE_VENGEFUL, "Large Vengeful Will Bud");

        // Will Clusters
        add(BMBlocks.WILL_CLUSTER_RAW, "Raw Will Cluster");
        add(BMBlocks.WILL_CLUSTER_CORROSIVE, "Corrosive Will Cluster");
        add(BMBlocks.WILL_CLUSTER_DESTRUCTIVE, "Destructive Will Cluster");
        add(BMBlocks.WILL_CLUSTER_STEADFAST, "Steadfast Will Cluster");
        add(BMBlocks.WILL_CLUSTER_VENGEFUL, "Vengeful Will Cluster");

        // Will Shards
        add(BMItems.WILL_SHARD_RAW.get(), "Raw Will Shard");
        add(BMItems.WILL_SHARD_CORROSIVE.get(), "Corrosive Will Shard");
        add(BMItems.WILL_SHARD_DESTRUCTIVE.get(), "Destructive Will Shard");
        add(BMItems.WILL_SHARD_STEADFAST.get(), "Steadfast Will Shard");
        add(BMItems.WILL_SHARD_VENGEFUL.get(), "Vengeful Will Shard");

        // Will Crystals
        add(BMItems.WILL_CRYSTAL_RAW.get(), "Raw Will Crystal");
        add(BMItems.WILL_CRYSTAL_CORROSIVE.get(), "Corrosive Will Crystal");
        add(BMItems.WILL_CRYSTAL_DESTRUCTIVE.get(), "Destructive Will Crystal");
        add(BMItems.WILL_CRYSTAL_STEADFAST.get(), "Steadfast Will Crystal");
        add(BMItems.WILL_CRYSTAL_VENGEFUL.get(), "Vengeful Will Crystal");

        // Will Catalysts
        add(BMItems.WILL_CATALYST_RAW.get(), "Raw Will Catalyst");
        add(BMItems.WILL_CATALYST_CORROSIVE.get(), "Corrosive Will Catalyst");
        add(BMItems.WILL_CATALYST_DESTRUCTIVE.get(), "Destructive Will Catalyst");
        add(BMItems.WILL_CATALYST_STEADFAST.get(), "Steadfast Will Catalyst");
        add(BMItems.WILL_CATALYST_VENGEFUL.get(), "Vengeful Will Catalyst");

        //Soul Gems
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

        add(BMItems.WILL_ROUTING_WAND.get(), "Routing Wand");
        addTooltip("will", "Will Quality: %s");
        for (EnumWillType type : EnumWillType.types()) {
            addTooltip("current_type." + type.getSerializedName(), String.format("Contains: %s Will", type.toCapitalized()));
            add(BMItems.WILL_ROUTING_WAND.get().getDescriptionId() + "." + type.getSerializedName(), "Routing Wand (" + type.toCapitalized() + ")");
            add(BMItems.MANIFESTED_WILL.get().getDescriptionId() + "." + type.getSerializedName(), "Manifested " + type.toCapitalized() + " Will");
        }

        addTooltip("routing.link.success", "Successfully linked to %s");
        addTooltip("routing.link.fail", "Failed to link to %s");
        addTooltip("routing.unlink.success", "Successfully removed %s");
        addTooltip("routing.unlink.fail", "Failed to remove %s");
        addTooltip("routing.will." + RAW.getSerializedName() + ".on", "Now using with Raw Will");
        addTooltip("routing.will." + RAW.getSerializedName() + ".off", "No longer using Raw Will");

        addTooltip("routing.will." + CORROSIVE.getSerializedName() + ".on", "Now using with Corrosive Will");
        addTooltip("routing.will." + CORROSIVE.getSerializedName() + ".off", "No longer using Corrosive Will");

        addTooltip("routing.will." + DESTRUCTIVE.getSerializedName() + ".on", "Now using with Destructive Will");
        addTooltip("routing.will." + DESTRUCTIVE.getSerializedName() + ".off", "No longer using Destructive Will");

        addTooltip("routing.will." + STEADFAST.getSerializedName() + ".on", "Now using with Steadfast Will");
        addTooltip("routing.will." + STEADFAST.getSerializedName() + ".off", "No longer using Steadfast Will");

        addTooltip("routing.will." + VENGEFUL.getSerializedName() + ".on", "Now using with Vengeful Will");
        addTooltip("routing.will." + VENGEFUL.getSerializedName() + ".off", "No longer using Vengeful Will");

        add(BMBlocks.ALCHEMY_TABLE, "Alchemy Table");
        addTooltip("alchemy_table.orb_error.title", "Orb Error");
        addTooltip("alchemy_table.orb_error.text", "Blood Orb not bound or missing");
        addTooltip("alchemy_table.essence_error.title", "Life Essence Error");
        addTooltip("alchemy_table.essence_error.text", "Not enough Life Essence in Soul Network");
        addTooltip("alchemy_table.stack_limit_toggle", "Toggle Max Stack Amount for Input Slots");

        //Living Armour and upgrades
        add("item_group.bloodmagic.main", "Blood Magic");
        add("item_group.bloodmagic.tomes", "Blood Magic Upgrade Tomes");
        add("item_group.bloodmagic.trainers", "Blood Magic Trainer Tomes");

        add(BMItems.LIVING_HELMET.get(), "Living Helmet");
        add(BMItems.LIVING_PLATE.get(), "Living Plate");
        add(BMItems.LIVING_LEGGINGS.get(), "Living Leggings");
        add(BMItems.LIVING_BOOTS.get(), "Living Boots");
        add(BMItems.UPGRADE_TOME.get(), "Upgrade Tome");

        add(BMBlocks.LIVING_STATION, "Living Upgrade Station");
        add(BMItems.UPGRADE_SCRAP.get(), "Upgrade Tome Scrap");
        add(BMItems.SYNTHETIC_POINT.get(), "Synthetic Upgrade Points");
        addTooltip("scrap", "Contained Upgrade Points: %s");

        add(BMItems.TRAINING_BRACELET.get(), "Living Training Bracelet");
        add("trainer.bloodmagic.allow_others", "Allow Others");
        add("trainer.bloodmagic.deny_others", "Deny Others");
        add("trainer.bloodmagic.save", "Save");

        addCommand("upgrade.get", "%s has the following upgrades:\n");
        addCommand("upgrade.set", "Set %s to %s exp for %s");
        addCommand("upgrade.no_armour", "The chestplate %s is wearing does not have an entry in the 'Living Armour Data' data map. Upgrades cannot take effect like this");
        addCommand("evolve.success", "Set evolved state to %s");
        addCommand("recalc.success", "Upgrades use up %s points");
        addCommand("limit.get", "%s is in '%s' mode and has the following limits:\n");
        addCommand("limit.set", "Set limit of %s to %s exp for %s");
        addCommand("limit.mode.allow", "allow others");
        addCommand("limit.mode.deny", "deny others");

        addTooltip("upgrade_points", "Upgrade Points: %s/%s");
        add("chat.bloodmagic.living_upgrade.level_up", "%s has levelled up to %s!");

        LivingUpgrades.translations(this::add);
        SigilData.translations(this::add);

        //Modopedia Guidebook lang-keys (was Patchouli)
        addBook("name", "Sanguine Scientiem");
        addBook("landing_text", "Welcome to $(blood)Blood Magic$()! \n\n$(bmentry:utility/nyi)A lot of stuff$() isn't yet implemented, so please excuse our dust. \n\nClick $(bmentry:utility/getting_started)HERE$() to get started. If you find any bugs, please report them on our $(l:https://github.com/WayofTime/BloodMagic/issues)Github$().");
        addBook("subtitle", "Alchemical Wizardry");
    }

    public void addBook(String key, String value) {
        add("guide.bloodmagic." + key, value);
    }

    public void addCommand(String key, String value) {
        add("commands.bloodmagic." + key, value);
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
