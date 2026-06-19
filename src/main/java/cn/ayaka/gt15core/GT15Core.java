package cn.ayaka.gt15core;

import cn.ayaka.gt15core.command.GT15CoreCommands;
import cn.ayaka.gt15core.block.FactoryAssistBlock;
import cn.ayaka.gt15core.item.BatchPartsCardItem;
import cn.ayaka.gt15core.item.IOBlueprintItem;
import cn.ayaka.gt15core.item.MaintenanceKitItem;
import cn.ayaka.gt15core.item.OrderBridgeCardItem;
import cn.ayaka.gt15core.item.PatternMultiplierCardItem;
import cn.ayaka.gt15core.multiblock.ControllerSpec;
import cn.ayaka.gt15core.multiblock.MultiblockControllerBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(GT15Core.MOD_ID)
public class GT15Core {
    public static final String MOD_ID = "gt15core";
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);

    private static BlockBehaviour.Properties controllerProps() {
        return BlockBehaviour.Properties.of()
                .strength(4.0F, 8.0F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.METAL);
    }


    public static final RegistryObject<Item> MAINTENANCE_KIT = ITEMS.register("maintenance_kit",
            () -> new MaintenanceKitItem(new Item.Properties().stacksTo(1).durability(512)));
    public static final RegistryObject<Item> IO_BLUEPRINT = ITEMS.register("io_blueprint",
            () -> new IOBlueprintItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PATTERN_MULTIPLIER_CARD = ITEMS.register("pattern_multiplier_card",
            () -> new PatternMultiplierCardItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BATCH_PARTS_CARD = ITEMS.register("batch_parts_card",
            () -> new BatchPartsCardItem(new Item.Properties().stacksTo(1).durability(1024)));
    public static final RegistryObject<Item> ORDER_BRIDGE_CARD = ITEMS.register("order_bridge_card",
            () -> new OrderBridgeCardItem(new Item.Properties().stacksTo(1)));


    // v1.1: real task-detectable multiblock controller blocks.
    public static final RegistryObject<Block> LV_COMPACT_WORKSHOP_CONTROLLER = BLOCKS.register("lv_compact_workshop_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.LV_COMPACT_WORKSHOP));
    public static final RegistryObject<Block> MV_SCREENING_YARD_CONTROLLER = BLOCKS.register("mv_screening_yard_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.MV_SCREENING_YARD));
    public static final RegistryObject<Block> HV_OIL_DISTILLATION_TOWER_CONTROLLER = BLOCKS.register("hv_oil_distillation_tower_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.HV_OIL_DISTILLATION_TOWER));
    public static final RegistryObject<Block> HV_PETROCHEMICAL_WORKSHOP_CONTROLLER = BLOCKS.register("hv_petrochemical_workshop_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.HV_PETROCHEMICAL_WORKSHOP));
    public static final RegistryObject<Block> EV_CLEANROOM_WAFER_LINE_CONTROLLER = BLOCKS.register("ev_cleanroom_wafer_line_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.EV_CLEANROOM_WAFER_LINE));
    public static final RegistryObject<Block> IV_VACUUM_FREEZING_TOWER_CONTROLLER = BLOCKS.register("iv_vacuum_freezing_tower_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.IV_VACUUM_FREEZING_TOWER));
    public static final RegistryObject<Block> AE2_GT_ORDER_BRIDGE_CONTROLLER = BLOCKS.register("ae2_gt_order_bridge_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.AE2_GT_ORDER_BRIDGE));
    public static final RegistryObject<Block> LUV_PRECIOUS_METAL_REFINERY_CONTROLLER = BLOCKS.register("luv_precious_metal_refinery_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.LUV_PRECIOUS_METAL_REFINERY));
    public static final RegistryObject<Block> LUV_NANOTECH_WORKSHOP_CONTROLLER = BLOCKS.register("luv_nanotech_workshop_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.LUV_NANOTECH_WORKSHOP));
    public static final RegistryObject<Block> ZPM_PLASMA_PREPARATION_TOWER_CONTROLLER = BLOCKS.register("zpm_plasma_preparation_tower_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.ZPM_PLASMA_PREPARATION_TOWER));
    public static final RegistryObject<Block> ZPM_FUSION_PREHEATER_CONTROLLER = BLOCKS.register("zpm_fusion_preheater_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.ZPM_FUSION_PREHEATER));
    public static final RegistryObject<Block> ZPM_QUANTUM_CIRCUIT_ASSEMBLY_CONTROLLER = BLOCKS.register("zpm_quantum_circuit_assembly_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.ZPM_QUANTUM_CIRCUIT_ASSEMBLY));

    public static final RegistryObject<Block> UV_FUSION_INDUSTRIALIZER_CONTROLLER = BLOCKS.register("uv_fusion_industrializer_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.UV_FUSION_INDUSTRIALIZER));
    public static final RegistryObject<Block> UV_EXTREME_ORE_PROCESSOR_CONTROLLER = BLOCKS.register("uv_extreme_ore_processor_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.UV_EXTREME_ORE_PROCESSOR));
    public static final RegistryObject<Block> UV_SPACE_MATERIAL_FOUNDRY_CONTROLLER = BLOCKS.register("uv_space_material_foundry_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.UV_SPACE_MATERIAL_FOUNDRY));
    public static final RegistryObject<Block> UHV_DIMENSION_RESOURCE_MATRIX_CONTROLLER = BLOCKS.register("uhv_dimension_resource_matrix_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.UHV_DIMENSION_RESOURCE_MATRIX));
    public static final RegistryObject<Block> UHV_DATA_WAFER_FAB_CONTROLLER = BLOCKS.register("uhv_data_wafer_fab_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.UHV_DATA_WAFER_FAB));
    public static final RegistryObject<Block> UHV_VOID_MINER_CONTROLLER = BLOCKS.register("uhv_void_miner_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.UHV_VOID_MINER));
    public static final RegistryObject<Block> UEV_ANTIMATTER_CONDENSER_CONTROLLER = BLOCKS.register("uev_antimatter_condenser_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.UEV_ANTIMATTER_CONDENSER));
    public static final RegistryObject<Block> UEV_COSMIC_MATERIAL_SYNTHESIZER_CONTROLLER = BLOCKS.register("uev_cosmic_material_synthesizer_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.UEV_COSMIC_MATERIAL_SYNTHESIZER));
    public static final RegistryObject<Block> UEV_ORDER_SUPERCOMPUTER_CONTROLLER = BLOCKS.register("uev_order_supercomputer_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.UEV_ORDER_SUPERCOMPUTER));
    public static final RegistryObject<Block> UIV_DEEP_SPACE_CHEMICAL_PLANT_CONTROLLER = BLOCKS.register("uiv_deep_space_chemical_plant_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.UIV_DEEP_SPACE_CHEMICAL_PLANT));
    public static final RegistryObject<Block> UIV_ULTIMATE_ENERGY_NEXUS_CONTROLLER = BLOCKS.register("uiv_ultimate_energy_nexus_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.UIV_ULTIMATE_ENERGY_NEXUS));
    public static final RegistryObject<Block> UIV_STELLAR_LOGISTICS_GATE_CONTROLLER = BLOCKS.register("uiv_stellar_logistics_gate_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.UIV_STELLAR_LOGISTICS_GATE));


    // v1.2: lightweight factory assist gameplay blocks. They are diagnostic/task anchors first,
    // later upgradeable to real BE/menu implementations without changing quest IDs.
    public static final RegistryObject<Block> ORDER_CACHE_BANK = BLOCKS.register("order_cache_bank",
            () -> new FactoryAssistBlock(controllerProps(), "订单缓存银行",
                    "缓存 AE2-GT 大订单快照，避免每次递归重算。",
                    "建议放在 UEV 订单超算中心附近，作为大批量合成入口。"));
    public static final RegistryObject<Block> PATTERN_ROUTER_NODE = BLOCKS.register("pattern_router_node",
            () -> new FactoryAssistBlock(controllerProps(), "样板路由节点",
                    "按阶段/机器/材料命名规则整理样板路线。",
                    "用于把处理样板送到对应机器池，不鼓励堆接口硬撑。"));
    public static final RegistryObject<Block> ENERGY_FAILSAFE_RELAY = BLOCKS.register("energy_failsafe_relay",
            () -> new FactoryAssistBlock(controllerProps(), "能源保险继电器",
                    "用于大型机器启动前检查缓冲与电网分区。",
                    "和 Omni Battery 搭配，减少后期全厂掉电。"));
    public static final RegistryObject<Block> VOID_RESOURCE_ANCHOR = BLOCKS.register("void_resource_anchor",
            () -> new FactoryAssistBlock(controllerProps(), "虚空资源锚点",
                    "标记虚空采掘/维度资源矩阵的资源来源。",
                    "不凭空生成材料，只作为任务、配方和后续虚空矿井逻辑锚点。"));
    public static final RegistryObject<Block> PARALLEL_FACTORY_FRAME = BLOCKS.register("parallel_factory_frame",
            () -> new FactoryAssistBlock(controllerProps(), "并行工厂框架",
                    "用于 UXV/OpV 巨构机器的并行升级框架。",
                    "后期用大型机器解决堆量，而不是摆几千台小机器。"));
    public static final RegistryObject<Block> CREATIVE_COMPLIANCE_CORE = BLOCKS.register("creative_compliance_core",
            () -> new FactoryAssistBlock(controllerProps(), "创造合规核心",
                    "MAX 终章毕业证明：创造级能力必须来自完整自动化闭环。",
                    "只作为毕业门槛与检测核心，不破坏主线玩法。"));

    public static final RegistryObject<Block> UXV_PARALLEL_FACTORY_CORE_CONTROLLER = BLOCKS.register("uxv_parallel_factory_core_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.UXV_PARALLEL_FACTORY_CORE));
    public static final RegistryObject<Block> UXV_STELLAR_FORGE_CONTROLLER = BLOCKS.register("uxv_stellar_forge_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.UXV_STELLAR_FORGE));
    public static final RegistryObject<Block> UXV_MEGA_DATA_NEXUS_CONTROLLER = BLOCKS.register("uxv_mega_data_nexus_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.UXV_MEGA_DATA_NEXUS));
    public static final RegistryObject<Block> OPV_OMNIVERSAL_RESOURCE_LATTICE_CONTROLLER = BLOCKS.register("opv_omniversal_resource_lattice_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.OPV_OMNIVERSAL_RESOURCE_LATTICE));
    public static final RegistryObject<Block> OPV_GIGA_ASSEMBLY_MONUMENT_CONTROLLER = BLOCKS.register("opv_giga_assembly_monument_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.OPV_GIGA_ASSEMBLY_MONUMENT));
    public static final RegistryObject<Block> OPV_FACTORY_GOVERNOR_CONTROLLER = BLOCKS.register("opv_factory_governor_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.OPV_FACTORY_GOVERNOR));
    public static final RegistryObject<Block> MAX_CREATIVE_PROVING_GROUND_CONTROLLER = BLOCKS.register("max_creative_proving_ground_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.MAX_CREATIVE_PROVING_GROUND));
    public static final RegistryObject<Block> MAX_AUTOMATION_ARCHIVE_CONTROLLER = BLOCKS.register("max_automation_archive_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.MAX_AUTOMATION_ARCHIVE));
    public static final RegistryObject<Block> MAX_LIGHTTECH_ASCENSION_CORE_CONTROLLER = BLOCKS.register("max_lighttech_ascension_core_controller",
            () -> new MultiblockControllerBlock(controllerProps(), ControllerSpec.MAX_LIGHTTECH_ASCENSION_CORE));

    public static final RegistryObject<Item> LV_COMPACT_WORKSHOP_CONTROLLER_ITEM = ITEMS.register("lv_compact_workshop_controller",
            () -> new BlockItem(LV_COMPACT_WORKSHOP_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> MV_SCREENING_YARD_CONTROLLER_ITEM = ITEMS.register("mv_screening_yard_controller",
            () -> new BlockItem(MV_SCREENING_YARD_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> HV_OIL_DISTILLATION_TOWER_CONTROLLER_ITEM = ITEMS.register("hv_oil_distillation_tower_controller",
            () -> new BlockItem(HV_OIL_DISTILLATION_TOWER_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> HV_PETROCHEMICAL_WORKSHOP_CONTROLLER_ITEM = ITEMS.register("hv_petrochemical_workshop_controller",
            () -> new BlockItem(HV_PETROCHEMICAL_WORKSHOP_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> EV_CLEANROOM_WAFER_LINE_CONTROLLER_ITEM = ITEMS.register("ev_cleanroom_wafer_line_controller",
            () -> new BlockItem(EV_CLEANROOM_WAFER_LINE_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> IV_VACUUM_FREEZING_TOWER_CONTROLLER_ITEM = ITEMS.register("iv_vacuum_freezing_tower_controller",
            () -> new BlockItem(IV_VACUUM_FREEZING_TOWER_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> AE2_GT_ORDER_BRIDGE_CONTROLLER_ITEM = ITEMS.register("ae2_gt_order_bridge_controller",
            () -> new BlockItem(AE2_GT_ORDER_BRIDGE_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUV_PRECIOUS_METAL_REFINERY_CONTROLLER_ITEM = ITEMS.register("luv_precious_metal_refinery_controller",
            () -> new BlockItem(LUV_PRECIOUS_METAL_REFINERY_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUV_NANOTECH_WORKSHOP_CONTROLLER_ITEM = ITEMS.register("luv_nanotech_workshop_controller",
            () -> new BlockItem(LUV_NANOTECH_WORKSHOP_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> ZPM_PLASMA_PREPARATION_TOWER_CONTROLLER_ITEM = ITEMS.register("zpm_plasma_preparation_tower_controller",
            () -> new BlockItem(ZPM_PLASMA_PREPARATION_TOWER_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> ZPM_FUSION_PREHEATER_CONTROLLER_ITEM = ITEMS.register("zpm_fusion_preheater_controller",
            () -> new BlockItem(ZPM_FUSION_PREHEATER_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> ZPM_QUANTUM_CIRCUIT_ASSEMBLY_CONTROLLER_ITEM = ITEMS.register("zpm_quantum_circuit_assembly_controller",
            () -> new BlockItem(ZPM_QUANTUM_CIRCUIT_ASSEMBLY_CONTROLLER.get(), new Item.Properties()));

    public static final RegistryObject<Item> UV_FUSION_INDUSTRIALIZER_CONTROLLER_ITEM = ITEMS.register("uv_fusion_industrializer_controller",
            () -> new BlockItem(UV_FUSION_INDUSTRIALIZER_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> UV_EXTREME_ORE_PROCESSOR_CONTROLLER_ITEM = ITEMS.register("uv_extreme_ore_processor_controller",
            () -> new BlockItem(UV_EXTREME_ORE_PROCESSOR_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> UV_SPACE_MATERIAL_FOUNDRY_CONTROLLER_ITEM = ITEMS.register("uv_space_material_foundry_controller",
            () -> new BlockItem(UV_SPACE_MATERIAL_FOUNDRY_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> UHV_DIMENSION_RESOURCE_MATRIX_CONTROLLER_ITEM = ITEMS.register("uhv_dimension_resource_matrix_controller",
            () -> new BlockItem(UHV_DIMENSION_RESOURCE_MATRIX_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> UHV_DATA_WAFER_FAB_CONTROLLER_ITEM = ITEMS.register("uhv_data_wafer_fab_controller",
            () -> new BlockItem(UHV_DATA_WAFER_FAB_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> UHV_VOID_MINER_CONTROLLER_ITEM = ITEMS.register("uhv_void_miner_controller",
            () -> new BlockItem(UHV_VOID_MINER_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> UEV_ANTIMATTER_CONDENSER_CONTROLLER_ITEM = ITEMS.register("uev_antimatter_condenser_controller",
            () -> new BlockItem(UEV_ANTIMATTER_CONDENSER_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> UEV_COSMIC_MATERIAL_SYNTHESIZER_CONTROLLER_ITEM = ITEMS.register("uev_cosmic_material_synthesizer_controller",
            () -> new BlockItem(UEV_COSMIC_MATERIAL_SYNTHESIZER_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> UEV_ORDER_SUPERCOMPUTER_CONTROLLER_ITEM = ITEMS.register("uev_order_supercomputer_controller",
            () -> new BlockItem(UEV_ORDER_SUPERCOMPUTER_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> UIV_DEEP_SPACE_CHEMICAL_PLANT_CONTROLLER_ITEM = ITEMS.register("uiv_deep_space_chemical_plant_controller",
            () -> new BlockItem(UIV_DEEP_SPACE_CHEMICAL_PLANT_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> UIV_ULTIMATE_ENERGY_NEXUS_CONTROLLER_ITEM = ITEMS.register("uiv_ultimate_energy_nexus_controller",
            () -> new BlockItem(UIV_ULTIMATE_ENERGY_NEXUS_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> UIV_STELLAR_LOGISTICS_GATE_CONTROLLER_ITEM = ITEMS.register("uiv_stellar_logistics_gate_controller",
            () -> new BlockItem(UIV_STELLAR_LOGISTICS_GATE_CONTROLLER.get(), new Item.Properties()));


    public static final RegistryObject<Item> ORDER_CACHE_BANK_ITEM = ITEMS.register("order_cache_bank",
            () -> new BlockItem(ORDER_CACHE_BANK.get(), new Item.Properties()));
    public static final RegistryObject<Item> PATTERN_ROUTER_NODE_ITEM = ITEMS.register("pattern_router_node",
            () -> new BlockItem(PATTERN_ROUTER_NODE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENERGY_FAILSAFE_RELAY_ITEM = ITEMS.register("energy_failsafe_relay",
            () -> new BlockItem(ENERGY_FAILSAFE_RELAY.get(), new Item.Properties()));
    public static final RegistryObject<Item> VOID_RESOURCE_ANCHOR_ITEM = ITEMS.register("void_resource_anchor",
            () -> new BlockItem(VOID_RESOURCE_ANCHOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> PARALLEL_FACTORY_FRAME_ITEM = ITEMS.register("parallel_factory_frame",
            () -> new BlockItem(PARALLEL_FACTORY_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> CREATIVE_COMPLIANCE_CORE_ITEM = ITEMS.register("creative_compliance_core",
            () -> new BlockItem(CREATIVE_COMPLIANCE_CORE.get(), new Item.Properties()));

    public static final RegistryObject<Item> UXV_PARALLEL_FACTORY_CORE_CONTROLLER_ITEM = ITEMS.register("uxv_parallel_factory_core_controller",
            () -> new BlockItem(UXV_PARALLEL_FACTORY_CORE_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> UXV_STELLAR_FORGE_CONTROLLER_ITEM = ITEMS.register("uxv_stellar_forge_controller",
            () -> new BlockItem(UXV_STELLAR_FORGE_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> UXV_MEGA_DATA_NEXUS_CONTROLLER_ITEM = ITEMS.register("uxv_mega_data_nexus_controller",
            () -> new BlockItem(UXV_MEGA_DATA_NEXUS_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> OPV_OMNIVERSAL_RESOURCE_LATTICE_CONTROLLER_ITEM = ITEMS.register("opv_omniversal_resource_lattice_controller",
            () -> new BlockItem(OPV_OMNIVERSAL_RESOURCE_LATTICE_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> OPV_GIGA_ASSEMBLY_MONUMENT_CONTROLLER_ITEM = ITEMS.register("opv_giga_assembly_monument_controller",
            () -> new BlockItem(OPV_GIGA_ASSEMBLY_MONUMENT_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> OPV_FACTORY_GOVERNOR_CONTROLLER_ITEM = ITEMS.register("opv_factory_governor_controller",
            () -> new BlockItem(OPV_FACTORY_GOVERNOR_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> MAX_CREATIVE_PROVING_GROUND_CONTROLLER_ITEM = ITEMS.register("max_creative_proving_ground_controller",
            () -> new BlockItem(MAX_CREATIVE_PROVING_GROUND_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> MAX_AUTOMATION_ARCHIVE_CONTROLLER_ITEM = ITEMS.register("max_automation_archive_controller",
            () -> new BlockItem(MAX_AUTOMATION_ARCHIVE_CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<Item> MAX_LIGHTTECH_ASCENSION_CORE_CONTROLLER_ITEM = ITEMS.register("max_lighttech_ascension_core_controller",
            () -> new BlockItem(MAX_LIGHTTECH_ASCENSION_CORE_CONTROLLER.get(), new Item.Properties()));

    public GT15Core() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modBus);
        BLOCKS.register(modBus);
        MinecraftForge.EVENT_BUS.addListener(this::onRegisterCommands);
    }

    private void onRegisterCommands(RegisterCommandsEvent event) {
        GT15CoreCommands.register(event.getDispatcher());
    }
}
