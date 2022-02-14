package yusama125718_209282ihcuobust.man10respawnanchor;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import static yusama125718_209282ihcuobust.man10respawnanchor.Man10RespawnAnchor.*;

public class Respawn extends Thread
{
    public void start()
    {
        int i=0;
        aaa: for (int k = 0;k<1;k++)
        {
            for (i=0;i < targetworld.size();i++)
            {
                if (((respawnplayer.getLocation()).getWorld()).getName().equals(targetworld.get(i)))
                {
                    break aaa;
                }
            }
            i = 0;
        }
        Location respawnlocation = respawnplayer.getLocation();
        respawnlocation.setX(respawnx.get(i));
        respawnlocation.setY(respawny.get(i));
        respawnlocation.setZ(respawnz.get(i));
        respawnlocation.setYaw(respawnyaw.get(i));
        respawnlocation.setPitch(respawnpitch.get(i));
        respawnlocation.setWorld(Bukkit.getWorld(respawnworld.get(i)));
        respawnplayer.teleport(respawnlocation);
        respawnplayer.sendMessage("§l[§fMan10Spawn§f§l]§eリスポーンしました");
    }
}
