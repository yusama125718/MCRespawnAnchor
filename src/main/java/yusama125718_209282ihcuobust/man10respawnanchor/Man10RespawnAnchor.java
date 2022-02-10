package yusama125718_209282ihcuobust.man10respawnanchor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static org.bukkit.Bukkit.getPlayer;

public final class Man10RespawnAnchor extends JavaPlugin
{
    double respawnyawd;
    float respawnyaw;
    private Man10RespawnAnchor mspawn;
    double respawnx;
    double respawny;
    double respawnz;
    double respawnhelth;
    double respawnpitchd;
    float respawnpitch;
    int respawnfood;
    World respawnworld;
    Player respawnplayer;

    @Override
    public void onEnable()
    {
        this.mspawn = this;
        saveDefaultConfig();
        try
        {
            respawnworld = Bukkit.getWorld(getConfig().getString("spawnworld"));
        }
        catch (IllegalArgumentException e)
        {
            Bukkit.broadcast("§l[§fMan10Spawn§f§l]§rリスポーンするワールドのロードに失敗しました","mspawn.op");
        }
        respawnyawd = mspawn.getConfig().getDouble("spawnyaw");
        respawnx = mspawn.getConfig().getDouble("spawnx");
        respawny = mspawn.getConfig().getDouble("spawny");
        respawnz = mspawn.getConfig().getDouble("spawnz");
        respawnpitchd = mspawn.getConfig().getDouble("spawnpitch");
        respawnyaw = (float) respawnyawd;
        respawnpitch = (float) respawnpitchd;
        respawnhelth = mspawn.getConfig().getDouble("respawnhelth");
        respawnfood = mspawn.getConfig().getInt("respawnfood");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!sender.hasPermission("mspawn.player"))
        {
            sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
            return true;
        }
        switch (args.length)
        {
            case 1:
            {
                if (args[0].equals("help"))
                {
                    if (sender.hasPermission("mspawn.op"))
                    {
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn set : spawn地点を現在地にセットします");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn reload : configをリロードします");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn set helth : リスポーン時の体力を設定します");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn set food : リスポーン時の満腹度を設定します");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn respawn [ユーザー名] : 特定のユーザーをリスポーンします");
                    }
                    sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn respawn : リスポーンします");
                }
                if (args[0].equals("set"))
                {
                    if (!(sender instanceof Player))
                    {
                        sender.sendMessage(("§c[manchiro]Player以外は実行できません"));
                        return true;
                    }
                    Location pLocation = ((Player) sender).getLocation();
                    double playerx = pLocation.getX();
                    respawnx = pLocation.getX();
                    double playery = pLocation.getY();
                    respawny = pLocation.getY();
                    double playerz = pLocation.getZ();
                    respawnz = pLocation.getZ();
                    double playeryaw = pLocation.getYaw();
                    respawnyaw = pLocation.getYaw();
                    double playerpitch = pLocation.getPitch();
                    respawnpitch = pLocation.getPitch();
                    String playerworld = pLocation.getWorld().getName();
                    respawnworld = pLocation.getWorld();
                    mspawn.getConfig().set("spawnx",playerx);
                    mspawn.getConfig().set("spawny",playery);
                    mspawn.getConfig().set("spawnz",playerz);
                    mspawn.getConfig().set("spawnyaw",playeryaw);
                    mspawn.getConfig().set("spawnpitch",playerpitch);
                    mspawn.getConfig().set("spawnworld",playerworld);
                    mspawn.saveConfig();
                    sender.sendMessage("§l[§fMan10Spawn§f§l]§eセットしました");
                    return true;
                }
                if (args[0].equals("respawn"))
                {
                    if (!(sender instanceof Player))
                    {
                        sender.sendMessage(("§l[§fMan10Spawn§f§l]§cPlayer以外は実行できません"));
                        return true;
                    }
                    respawnplayer = ((Player) sender).getPlayer();
                    Location pLocation = respawnplayer.getLocation();
                    pLocation.setX(respawnx);
                    pLocation.setY(respawny);
                    pLocation.setZ(respawnz);
                    pLocation.setYaw(respawnyaw);
                    pLocation.setPitch(respawnpitch);
                    pLocation.setWorld(respawnworld);
                    respawnplayer.teleport(pLocation);
                    sender.sendMessage("§l[§fMan10Spawn§f§l]§eリスポーンしました");
                    return true;
                }
                if (args[0].equals("reload"))
                {
                    if (!sender.hasPermission("mspawn.op"))
                    {
                        sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
                        return true;
                    }
                    respawnworld = Bukkit.getWorld(getConfig().getString("spawnworld"));
                    respawnyawd = mspawn.getConfig().getDouble("spawnyaw");
                    respawnx = mspawn.getConfig().getDouble("spawnx");
                    respawny = mspawn.getConfig().getDouble("spawny");
                    respawnz = mspawn.getConfig().getDouble("spawnz");
                    respawnpitchd = mspawn.getConfig().getDouble("spawnpitch");
                    respawnfood = mspawn.getConfig().getInt("respawnfood");
                    respawnyaw = (float) respawnyawd;
                    respawnpitch = (float) respawnpitchd;
                    respawnhelth = mspawn.getConfig().getDouble("respawnhelth");
                    respawnfood = mspawn.getConfig().getInt("respawnfood");
                    sender.sendMessage("§l[§fMan10Spawn§f§l]§eリロードしました");
                    return true;
                }
                break;
            }
            case 2:
            {
                if (args[0].equals("respawn"))
                {
                    if (!sender.hasPermission("mspawn.op"))
                    {
                        sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
                        return true;
                    }
                    Player checkplayer = getPlayer(args[0]);
                    if (checkplayer == null)
                    {
                        sender.sendMessage("§l[§fMan10Spawn§f§l]§cそのプレイヤーは存在しません");
                        return false;
                    }
                    assert respawnplayer != null;
                    Location pLocation = respawnplayer.getLocation();
                    pLocation.setX(respawnx);
                    pLocation.setY(respawny);
                    pLocation.setZ(respawnz);
                    pLocation.setYaw(respawnyaw);
                    pLocation.setPitch(respawnpitch);
                    pLocation.setWorld(respawnworld);
                    respawnplayer.teleport(pLocation);
                    respawnplayer.setFoodLevel(respawnfood);
                    sender.sendMessage("§l[§fMan10Spawn§f§l]§e"+respawnplayer.getName()+"をリスポーンしました");
                    respawnplayer.sendMessage("§l[§fMan10Spawn§f§l]§eリスポーンしました");
                    return true;
                }
                break;
            }
            case 3:
            {
                if (args[0].equals("set")&&args[1].equals("helth"))
                {
                    if (!sender.hasPermission("mspawn.op"))
                    {
                        sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
                        return true;
                    }
                    boolean isNumeric = args[2].matches("-?\\d+");
                    if (!isNumeric)
                    {
                        sender.sendMessage("§l[§fMan10Spawn§f§l]§c体力は整数にしてください");
                        return true;
                    }
                    int sethelth = Integer.parseInt(args[2]);
                    if (sethelth>20||sethelth<1)
                    {
                        sender.sendMessage("§l[§fMan10Spawn§f§l]§c体力は1以上20以下の数字にしてください");
                        return true;
                    }
                    mspawn.getConfig().set("respawnhelth",sethelth);
                    mspawn.saveConfig();
                    sender.sendMessage("§l[§fMan10Spawn§f§l]§eセットしました");
                    return true;
                }
                if (!args[0].equals("set")&&args[1].equals("food"))
                {
                    if (sender.hasPermission("mspawn.op"))
                    {
                        sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
                        return true;
                    }
                    boolean isNumeric = args[2].matches("-?\\d+");
                    if (!isNumeric)
                    {
                        sender.sendMessage("§l[§fMan10Spawn§f§l]§c体力は整数にしてください");
                        return true;
                    }
                    int setfood = Integer.parseInt(args[2]);
                    if (setfood>20||setfood<0)
                    {
                        sender.sendMessage("§l[§fMan10Spawn§f§l]§c体力は0以上20以下の数字にしてください");
                        return true;
                    }
                    mspawn.getConfig().set("respawnfood",setfood);
                    mspawn.saveConfig();
                    sender.sendMessage("§l[§fMan10Spawn§f§l]§eセットしました");
                    return true;
                }
                break;
            }
            default:
            {
                if (sender.hasPermission("mspawn.op"))
                {
                    sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn set : spawn地点を現在地にセットします");
                    sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn reload : configをリロードします");
                    sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn set helth : リスポーン時の体力を設定します");
                    sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn set food : リスポーン時の満腹度を設定します");
                    sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn respawn [ユーザー名] : 特定のユーザーをリスポーンします");
                }
                sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn respawn : リスポーンします");
                return true;
            }
        }
        return true;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent player)
    {
        Bukkit.getLogger().info("メッセージ");
        respawnplayer = player.getPlayer();
        Location respawnlocation = player.getRespawnLocation();
        respawnlocation.setX(respawnx);
        respawnlocation.setY(respawny);
        respawnlocation.setZ(respawnz);
        respawnlocation.setYaw(respawnyaw);
        respawnlocation.setPitch(respawnpitch);
        respawnlocation.setWorld(respawnworld);
        respawnplayer.setHealth(respawnhelth);
        respawnplayer.setFoodLevel(respawnfood);
    }

    @Override
    public void onDisable() {}
}
