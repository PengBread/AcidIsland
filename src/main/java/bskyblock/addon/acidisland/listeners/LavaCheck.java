package bskyblock.addon.acidisland.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import bskyblock.addon.acidisland.AcidIsland;


public class LavaCheck implements Listener {

    AcidIsland addon;


    public LavaCheck(AcidIsland addon) {
        this.addon = addon;
    }

    /**
     * Removes stone generated by lava pouring on water
     * 
     * @param e - event
     */
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onCleanstoneGen(BlockFromToEvent e) {
        // Only do this in AcidIsland over world
        if (!e.getBlock().getWorld().equals(addon.getIslandWorld()) || addon.getSettings().getAcidDamage() <= 0
                || !(e.getToBlock().getType().equals(Material.WATER) || e.getToBlock().getType().equals(Material.STATIONARY_WATER))) {
            return;
        }
        Material prev = e.getToBlock().getType();        
        addon.getServer().getScheduler().runTask(addon.getBSkyBlock(), () -> {
            if (e.getToBlock().getType().equals(Material.STONE)) {
                e.getToBlock().setType(prev);
                e.getToBlock().getWorld().playSound(e.getToBlock().getLocation(), Sound.ENTITY_CREEPER_PRIMED, 1F, 2F);
            }
        });
    }


}