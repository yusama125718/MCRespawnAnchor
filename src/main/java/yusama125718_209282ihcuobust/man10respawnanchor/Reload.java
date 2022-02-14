package yusama125718_209282ihcuobust.man10respawnanchor;

import org.bukkit.Bukkit;

import java.util.Objects;
import java.util.UUID;

import static yusama125718_209282ihcuobust.man10respawnanchor.Man10RespawnAnchor.*;

public class Reload extends Thread
{
    public void start()
    {
        respawnyaw.clear();
        respawnyawd.clear();
        respawnx.clear();
        respawny.clear();
        respawnz.clear();
        respawnpitch.clear();
        respawnpitchd.clear();
        respawnworld.clear();
        exceptionplayers.clear();
        exceptionworlds.clear();
        mspawn.reloadConfig();
        respawnyawd.addAll(mspawn.getConfig().getDoubleList("spawnyaw"));
        double changeyawd;
        float changeyaw;
        for (int i = 0;i<(mspawn.getConfig().getDoubleList("spawnyaw")).size();i++)
        {
            changeyawd = respawnyawd.get(i);
            changeyaw = (float) (changeyawd);
            respawnyaw.add(changeyaw);
        }
        respawnx.addAll(mspawn.getConfig().getDoubleList("spawnx"));
        respawny.addAll(mspawn.getConfig().getDoubleList("spawny"));
        respawnz.addAll(mspawn.getConfig().getDoubleList("spawnz"));
        respawnpitchd.addAll(mspawn.getConfig().getDoubleList("spawnpitch"));
        double changepitchd;
        float changepitch;
        for (int i = 0;i<(mspawn.getConfig().getDoubleList("spawnpitch")).size();i++)
        {
            changepitchd = respawnpitchd.get(i);
            changepitch = (float) (changepitchd);
            respawnpitch.add(changepitch);
        }
        respawnworld.addAll(mspawn.getConfig().getStringList("spawnworld"));
        targetworld.addAll(mspawn.getConfig().getStringList("targetworld"));
        respawnhealth = mspawn.getConfig().getDouble("respawnhealth");
        if (respawnhealth>20)
        {
            respawnhealth = 20;
        }
        if (respawnhealth<1)
        {
            respawnhealth = 1;
        }
        respawnfood = mspawn.getConfig().getInt("respawnfood");
        respawnmessage = mspawn.getConfig().getString("respawnmessage");
        System = mspawn.getConfig().getBoolean("system");
        try
        {
            for (int i = 0; i < mspawn.getConfig().getList("exceptionplayerlist").size(); i++)
            {
                exceptionplayers.add((UUID) (mspawn.getConfig().getList("exceptionplayerlist")).get(i));
            }
        }
        catch (NullPointerException e)
        {
            Bukkit.broadcast("§l[§fMan10Spawn§f§l]§r除外するプレイヤーのロードに失敗しました","mspawn.op");
        }
        try
        {
            for (int i = 0; i < mspawn.getConfig().getList("exceptionworldlist").size(); i++)
            {
                exceptionworlds.add((String) Objects.requireNonNull(mspawn.getConfig().getList("exceptionworldlist")).get(i));
            }
        }
        catch (NullPointerException e)
        {
            Bukkit.broadcast("§l[§fMan10Spawn§f§l]§r除外するワールドのロードに失敗しました","mspawn.op");
        }
    }
}
