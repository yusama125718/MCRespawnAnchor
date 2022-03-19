package yusama125718_209282ihcuobust.man10respawnanchor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static yusama125718_209282ihcuobust.man10respawnanchor.Man10RespawnAnchor.*;

public class Event implements Listener {
    @EventHandler
    public void PlayerRespawnEvent(PlayerRespawnEvent event) throws InterruptedException {
        if (exceptionplayers.contains(event.getPlayer().getUniqueId()) || exceptionworlds.contains(event.getPlayer().getWorld().getName()) || !system)
        {
            return;
        }
        int i=0;
        aaa: for (i=0;i < targetworld.size();i++)
        {
            if (((event.getPlayer().getLocation()).getWorld()).getName().equals(targetworld.get(i)))
            {
                break aaa;
            }
        }
        if (i>targetworld.size() - 1)
        {
            i=0;
        }
        Location respawnlocation = event.getRespawnLocation();
        respawnlocation.setX(respawnx.get(i));
        respawnlocation.setY(respawny.get(i));
        respawnlocation.setZ(respawnz.get(i));
        respawnlocation.setYaw(respawnyaw.get(i));
        respawnlocation.setPitch(respawnpitch.get(i));
        respawnlocation.setWorld(Bukkit.getWorld(respawnworld.get(i)));
        event.setRespawnLocation(respawnlocation);
        if (dpenalty)
        {
            Thread.sleep(1);
            event.getPlayer().setHealth(respawnhealth);
            event.getPlayer().setFoodLevel(respawnfood);
            event.getPlayer().sendMessage(respawnmessage);
        }
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent joinEvent)
    {
        if (!JoinSystem)
        {
            return;
        }
        Player joinplayer = joinEvent.getPlayer();
        Location joinlocation = joinplayer.getLocation();
        joinlocation.setX(respawnx.get(0));
        joinlocation.setY(respawny.get(0));
        joinlocation.setZ(respawnz.get(0));
        joinlocation.setYaw(respawnyaw.get(0));
        joinlocation.setPitch(respawnpitch.get(0));
        joinlocation.setWorld(Bukkit.getWorld(respawnworld.get(0)));
        joinplayer.teleport(joinlocation);
    }

    @EventHandler
    public void PlayerChangedWorldEvent(PlayerChangedWorldEvent event)
    {
        if (!system)
        {
            return;
        }
        if (!MoveSystem)
        {
            return;
        }
        int i=0;
        aaa: for (i=0;i < movetargetworld.size();i++)
        {
            if (((event.getPlayer().getLocation()).getWorld()).getName().equals(movetargetworld.get(i)))
            {
                break aaa;
            }
        }
        if (i>movetargetworld.size())
        {
            return;
        }
        Player joinplayer = event.getPlayer();
        Location joinlocation = joinplayer.getLocation();
        joinlocation.setX(spawnx.get(i));
        joinlocation.setY(spawny.get(i));
        joinlocation.setZ(spawnz.get(i));
        joinlocation.setYaw(spawnyaw.get(i));
        joinlocation.setPitch(spawnpitch.get(i));
        joinplayer.teleport(joinlocation);
    }
}
