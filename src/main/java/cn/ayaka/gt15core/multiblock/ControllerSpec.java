package cn.ayaka.gt15core.multiblock;

import java.util.LinkedHashMap;
import java.util.Map;

public class ControllerSpec {
    private final String displayName;
    private final int radius;
    private final Map<String, Integer> requiredBlocks;

    public ControllerSpec(String displayName, int radius, Map<String, Integer> requiredBlocks) {
        this.displayName = displayName;
        this.radius = radius;
        this.requiredBlocks = requiredBlocks;
    }

    public String displayName() {
        return displayName;
    }

    public int radius() {
        return radius;
    }

    public Map<String, Integer> requiredBlocks() {
        return requiredBlocks;
    }

    private static Map<String, Integer> req(Object... values) {
        Map<String, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < values.length; i += 2) {
            map.put((String) values[i], (Integer) values[i + 1]);
        }
        return map;
    }

    public static final ControllerSpec LV_COMPACT_WORKSHOP = new ControllerSpec("LV 小型电力工坊", 4, req(
            "gtceu:lv_machine_hull", 1,
            "gtceu:lv_battery_buffer_4x", 1,
            "omnibattery:low_battery_block", 1
    ));

    public static final ControllerSpec MV_SCREENING_YARD = new ControllerSpec("MV 机械筛洗中心", 5, req(
            "exdeorum:mechanical_hammer", 1,
            "exdeorum:mechanical_sieve", 1,
            "gtceu:mv_machine_hull", 1
    ));

    public static final ControllerSpec HV_OIL_DISTILLATION_TOWER = new ControllerSpec("HV 石油蒸馏塔", 5, req(
            "gtceu:hv_machine_hull", 1,
            "gtceu:hv_chemical_reactor", 1,
            "minecraft:glass", 8
    ));

    public static final ControllerSpec HV_PETROCHEMICAL_WORKSHOP = new ControllerSpec("HV 石化工坊", 6, req(
            "gtceu:hv_machine_hull", 1,
            "gtceu:hv_chemical_reactor", 2,
            "omnibattery:advanced_battery_block", 1
    ));

    public static final ControllerSpec EV_CLEANROOM_WAFER_LINE = new ControllerSpec("EV 洁净室晶圆线", 6, req(
            "gtceu:ev_machine_hull", 1,
            "gtceu:ev_assembler", 1,
            "gtceu:cleanroom_controller", 1,
            "omnibattery:elite_battery_block", 1
    ));

    public static final ControllerSpec IV_VACUUM_FREEZING_TOWER = new ControllerSpec("IV 真空冷冻塔", 5, req(
            "gtceu:iv_machine_hull", 1,
            "gtceu:vacuum_freezer", 1,
            "gtceu:tungstensteel_block", 4,
            "omnibattery:ultimate_battery_block", 1
    ));

    public static final ControllerSpec AE2_GT_ORDER_BRIDGE = new ControllerSpec("IV AE2-GT 订单桥阵列", 6, req(
            "ae2:controller", 1,
            "ae2:pattern_provider", 1,
            "ae2:molecular_assembler", 1,
            "gtceu:iv_machine_hull", 1
    ));

    public static final ControllerSpec LUV_PRECIOUS_METAL_REFINERY = new ControllerSpec("LuV 贵金属精炼塔", 6, req(
            "gtceu:luv_machine_hull", 1,
            "gtceu:iv_electric_blast_furnace", 1,
            "gt15core:iv_vacuum_freezing_tower_controller", 1,
            "minecraft:gold_block", 4
    ));

    public static final ControllerSpec LUV_NANOTECH_WORKSHOP = new ControllerSpec("LuV 纳米电子工坊", 6, req(
            "gtceu:luv_machine_hull", 1,
            "ae2:pattern_provider", 1,
            "ae2:molecular_assembler", 1,
            "gt15core:ae2_gt_order_bridge_controller", 1
    ));

    public static final ControllerSpec ZPM_PLASMA_PREPARATION_TOWER = new ControllerSpec("ZPM 等离子预处理塔", 7, req(
            "gtceu:zpm_machine_hull", 1,
            "gtceu:luv_chemical_reactor", 1,
            "minecraft:glass", 12,
            "gt15core:luv_precious_metal_refinery_controller", 1
    ));

    public static final ControllerSpec ZPM_FUSION_PREHEATER = new ControllerSpec("ZPM 聚变预热环", 7, req(
            "gtceu:zpm_machine_hull", 1,
            "omnibattery:ultimate_battery_block", 1,
            "minecraft:diamond_block", 4,
            "gt15core:iv_vacuum_freezing_tower_controller", 1
    ));

    public static final ControllerSpec ZPM_QUANTUM_CIRCUIT_ASSEMBLY = new ControllerSpec("ZPM 量子电路装配阵列", 7, req(
            "gtceu:zpm_machine_hull", 1,
            "ae2:controller", 1,
            "ae2:pattern_provider", 1,
            "gt15core:luv_nanotech_workshop_controller", 1
    ));

    public static final ControllerSpec UV_FUSION_INDUSTRIALIZER = new ControllerSpec("UV 聚变产业化核心", 8, req(
            "gtceu:uv_machine_hull", 1,
            "gt15core:zpm_fusion_preheater_controller", 1,
            "gtceu:zpm_energy_input_hatch", 1,
            "minecraft:diamond_block", 8
    ));

    public static final ControllerSpec UV_EXTREME_ORE_PROCESSOR = new ControllerSpec("UV 极限矿物处理中心", 8, req(
            "gtceu:uv_machine_hull", 1,
            "gtceu:zpm_macerator", 1,
            "gtceu:zpm_electrolyzer", 1,
            "gtceu:zpm_centrifuge", 1
    ));

    public static final ControllerSpec UV_SPACE_MATERIAL_FOUNDRY = new ControllerSpec("UV 星际材料铸造阵列", 8, req(
            "gtceu:uv_machine_hull", 1,
            "gtceu:zpm_electric_blast_furnace", 1,
            "gt15core:zpm_plasma_preparation_tower_controller", 1,
            "minecraft:netherite_block", 1
    ));

    public static final ControllerSpec UHV_DIMENSION_RESOURCE_MATRIX = new ControllerSpec("UHV 维度资源矩阵", 9, req(
            "gtceu:uhv_machine_hull", 1,
            "gt15core:uv_extreme_ore_processor_controller", 1,
            "minecraft:end_portal_frame", 4,
            "minecraft:nether_star", 4
    ));

    public static final ControllerSpec UHV_DATA_WAFER_FAB = new ControllerSpec("UHV 数据晶圆工厂", 9, req(
            "gtceu:uhv_machine_hull", 1,
            "gt15core:zpm_quantum_circuit_assembly_controller", 1,
            "ae2:controller", 1,
            "ae2:molecular_assembler", 4
    ));

    public static final ControllerSpec UHV_VOID_MINER = new ControllerSpec("UHV 虚空采掘中枢", 9, req(
            "gtceu:uhv_machine_hull", 1,
            "gt15core:uv_extreme_ore_processor_controller", 1,
            "minecraft:beacon", 1,
            "minecraft:obsidian", 16
    ));

    public static final ControllerSpec UEV_ANTIMATTER_CONDENSER = new ControllerSpec("UEV 反物质冷凝器", 10, req(
            "gtceu:uev_machine_hull", 1,
            "gt15core:uhv_dimension_resource_matrix_controller", 1,
            "gt15core:uv_fusion_industrializer_controller", 1,
            "minecraft:nether_star", 8
    ));

    public static final ControllerSpec UEV_COSMIC_MATERIAL_SYNTHESIZER = new ControllerSpec("UEV 宇宙材料合成阵列", 10, req(
            "gtceu:uev_machine_hull", 1,
            "gt15core:uhv_data_wafer_fab_controller", 1,
            "gt15core:uv_space_material_foundry_controller", 1,
            "minecraft:dragon_egg", 1
    ));

    public static final ControllerSpec UEV_ORDER_SUPERCOMPUTER = new ControllerSpec("UEV 订单超算中心", 10, req(
            "gtceu:uev_machine_hull", 1,
            "gt15core:ae2_gt_order_bridge_controller", 1,
            "ae2:crafting_accelerator", 8,
            "ae2:controller", 1
    ));

    public static final ControllerSpec UIV_DEEP_SPACE_CHEMICAL_PLANT = new ControllerSpec("UIV 深空化工厂", 11, req(
            "gtceu:uiv_machine_hull", 1,
            "gt15core:uev_antimatter_condenser_controller", 1,
            "gtceu:zpm_chemical_reactor", 2,
            "minecraft:glass", 32
    ));

    public static final ControllerSpec UIV_ULTIMATE_ENERGY_NEXUS = new ControllerSpec("UIV 终极能源枢纽", 11, req(
            "gtceu:uiv_machine_hull", 1,
            "gt15core:uv_fusion_industrializer_controller", 1,
            "omnibattery:ultimate_battery_block", 4,
            "minecraft:redstone_block", 16
    ));

    public static final ControllerSpec UIV_STELLAR_LOGISTICS_GATE = new ControllerSpec("UIV 星门物流中枢", 11, req(
            "gtceu:uiv_machine_hull", 1,
            "gt15core:uev_order_supercomputer_controller", 1,
            "ae2:quantum_ring", 4,
            "ae2:controller", 1
    ));

    public static final ControllerSpec UXV_PARALLEL_FACTORY_CORE = new ControllerSpec("UXV 寰宇并行工厂核心", 12, req(
            "gtceu:uxv_machine_hull", 1,
            "gt15core:uiv_stellar_logistics_gate_controller", 1,
            "gt15core:parallel_factory_frame", 8,
            "gt15core:energy_failsafe_relay", 1
    ));

    public static final ControllerSpec UXV_STELLAR_FORGE = new ControllerSpec("UXV 恒星熔炉", 12, req(
            "gtceu:uxv_machine_hull", 1,
            "gt15core:uiv_ultimate_energy_nexus_controller", 1,
            "gt15core:uiv_deep_space_chemical_plant_controller", 1,
            "minecraft:beacon", 4
    ));

    public static final ControllerSpec UXV_MEGA_DATA_NEXUS = new ControllerSpec("UXV 兆级数据中枢", 12, req(
            "gtceu:uxv_machine_hull", 1,
            "gt15core:uev_order_supercomputer_controller", 1,
            "gt15core:order_cache_bank", 2,
            "gt15core:pattern_router_node", 4
    ));

    public static final ControllerSpec OPV_OMNIVERSAL_RESOURCE_LATTICE = new ControllerSpec("OpV 全域资源晶格", 13, req(
            "gtceu:opv_machine_hull", 1,
            "gt15core:uxv_parallel_factory_core_controller", 1,
            "gt15core:void_resource_anchor", 4,
            "minecraft:end_portal_frame", 8
    ));

    public static final ControllerSpec OPV_GIGA_ASSEMBLY_MONUMENT = new ControllerSpec("OpV 巨构装配纪念碑", 13, req(
            "gtceu:opv_machine_hull", 1,
            "gt15core:uxv_mega_data_nexus_controller", 1,
            "gt15core:parallel_factory_frame", 16,
            "ae2:crafting_accelerator", 16
    ));

    public static final ControllerSpec OPV_FACTORY_GOVERNOR = new ControllerSpec("OpV 全厂治理核心", 13, req(
            "gtceu:opv_machine_hull", 1,
            "gt15core:energy_failsafe_relay", 4,
            "gt15core:order_cache_bank", 4,
            "gt15core:uiv_stellar_logistics_gate_controller", 1
    ));

    public static final ControllerSpec MAX_CREATIVE_PROVING_GROUND = new ControllerSpec("MAX 创造合规试炼场", 14, req(
            "gtceu:max_machine_hull", 1,
            "gt15core:creative_compliance_core", 1,
            "gt15core:opv_factory_governor_controller", 1,
            "minecraft:nether_star", 16
    ));

    public static final ControllerSpec MAX_AUTOMATION_ARCHIVE = new ControllerSpec("MAX 毕业自动化档案馆", 14, req(
            "gtceu:max_machine_hull", 1,
            "gt15core:opv_giga_assembly_monument_controller", 1,
            "gt15core:uxv_mega_data_nexus_controller", 1,
            "gt15core:pattern_router_node", 8
    ));

    public static final ControllerSpec MAX_LIGHTTECH_ASCENSION_CORE = new ControllerSpec("MAX 绫华轻科技升华核心", 14, req(
            "gtceu:max_machine_hull", 1,
            "gt15core:max_creative_proving_ground_controller", 1,
            "gt15core:max_automation_archive_controller", 1,
            "gt15core:creative_compliance_core", 4
    ));
}