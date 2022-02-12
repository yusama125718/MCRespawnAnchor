package yusama125718_209282ihcuobust.man10respawnanchor;


import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Man10RespawnAnchor extends JavaPlugin implements Listener, CommandExecutor, TabCompleter
{
    double respawnyawd;
    float respawnyaw;
    JavaPlugin mspawn;
    double respawnx;
    double respawny;
    double respawnz;
    double respawnhealth;
    double respawnpitchd;
    float respawnpitch;
    int respawnfood;
    World respawnworld;
    Player respawnplayer;
    String respawnmessage;
    boolean System;
    List<UUID> exceptionplayers=new ArrayList<>();
    List<World> exceptionworlds=new ArrayList<>();

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
        respawnhealth = mspawn.getConfig().getDouble("respawnhealth");
        respawnfood = mspawn.getConfig().getInt("respawnfood");
        respawnmessage = mspawn.getConfig().getString("respawnmessage");
        System = mspawn.getConfig().getBoolean("system");
        exceptionplayers = (List<UUID>) mspawn.getConfig().getList("exceptionplayerlist");
        exceptionworlds = (List<World>) mspawn.getConfig().getList("exceptionworldlist");
        getServer().getPluginManager().registerEvents(this, this);
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
                if (args[0].equals("on"))
                {
                    if (!sender.hasPermission("mspawn.op"))
                    {
                        sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
                        return true;
                    }
                    System = true;
                    mspawn.getConfig().set("system",true);
                    sender.sendMessage("§l[§fMan10Spawn§f§l]§eONにしました");
                    saveConfig();
                }
                if (args[0].equals("off"))
                {
                    if (!sender.hasPermission("mspawn.op"))
                    {
                        sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
                        return true;
                    }
                    System = false;
                    mspawn.getConfig().set("system",false);
                    sender.sendMessage("§l[§fMan10Spawn§f§l]§eOFFにしました");
                    saveConfig();
                }
                if (args[0].equals("help"))
                {
                    if (sender.hasPermission("mspawn.op"))
                    {
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn set : mspanwのデフォルトのspawn地点を現在地にセットします");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn reload : configをリロードします");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn on : mspawnを有効化します");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn off : mspawnを無効化します");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn exception add [ユーザー名] : mspawnを無効化するプレイヤーを追加します");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn exception delete [ユーザー名] : mspawnを無効化するプレイヤーから除外します");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn exceptionw add [ワールド名] : mspawnを無効化するワールドを追加します");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn exceptionw delete [ワールド名] : mspawnを無効化するワールドから除外します");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn sethealth : リスポーン時の体力を設定します");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn setfood : リスポーン時の満腹度を設定します");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn message : リスポーン時のメッセージを設定します");
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
                    if (!System)
                    {
                        sender.sendMessage(("§l[§fMan10Spawn§f§l]§c現在OFFです"));
                        return true;
                    }
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
                    mspawn.reloadConfig();
                    respawnworld = Bukkit.getWorld(getConfig().getString("spawnworld"));
                    respawnyawd = mspawn.getConfig().getDouble("spawnyaw");
                    respawnx = mspawn.getConfig().getDouble("spawnx");
                    respawny = mspawn.getConfig().getDouble("spawny");
                    respawnz = mspawn.getConfig().getDouble("spawnz");
                    respawnpitchd = mspawn.getConfig().getDouble("spawnpitch");
                    respawnfood = mspawn.getConfig().getInt("respawnfood");
                    respawnyaw = (float) respawnyawd;
                    respawnpitch = (float) respawnpitchd;
                    respawnhealth = mspawn.getConfig().getDouble("respawnhealth");
                    respawnfood = mspawn.getConfig().getInt("respawnfood");
                    respawnmessage = mspawn.getConfig().getString("respawnmessage");
                    System = mspawn.getConfig().getBoolean("system");
                    exceptionplayers = (List<UUID>) mspawn.getConfig().getList("exceptionplayerlist");
                    exceptionworlds = (List<World>) mspawn.getConfig().getList("exceptionworldlist");
                    sender.sendMessage("§l[§fMan10Spawn§f§l]§eリロードしました");
                    return true;
                }
                break;
            }
            case 2:
            {
                if (args[0].equals("respawn"))
                {
                    if (!System)
                    {
                        sender.sendMessage(("§l[§fMan10Spawn§f§l]§c現在OFFです"));
                        return true;
                    }
                    if (!sender.hasPermission("mspawn.op"))
                    {
                        sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
                        return true;
                    }
                    respawnplayer = Bukkit.getPlayerExact(args[1]);
                    if (respawnplayer == null)
                    {
                        sender.sendMessage("§l[§fMan10Spawn§f§l]§cそのプレイヤーは存在しません");
                        return true;
                    }
                    Location pLocation = respawnplayer.getLocation();
                    pLocation.setX(respawnx);
                    pLocation.setY(respawny);
                    pLocation.setZ(respawnz);
                    pLocation.setYaw(respawnyaw);
                    pLocation.setPitch(respawnpitch);
                    pLocation.setWorld(respawnworld);
                    respawnplayer.teleport(pLocation);
                    sender.sendMessage("§l[§fMan10Spawn§f§l]§e"+respawnplayer.getName()+"をリスポーンしました");
                    respawnplayer.sendMessage("§l[§fMan10Spawn§f§l]§eリスポーンしました");
                    return true;
                }
                if (args[0].equals("sethealth"))
                {
                    if (!sender.hasPermission("mspawn.op"))
                    {
                        sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
                        return true;
                    }
                    boolean isNumeric = args[1].matches("-?\\d+");
                    if (!isNumeric)
                    {
                        sender.sendMessage("§l[§fMan10Spawn§f§l]§c体力は整数にしてください");
                        return true;
                    }
                    int sethealth = Integer.parseInt(args[1]);
                    if (sethealth>20||sethealth<1)
                    {
                        sender.sendMessage("§l[§fMan10Spawn§f§l]§c体力は1以上20以下の数字にしてください");
                        return true;
                    }
                    mspawn.getConfig().set("respawnhealth",sethealth);
                    mspawn.saveConfig();
                    respawnhealth = sethealth;
                    sender.sendMessage("§l[§fMan10Spawn§f§l]§eセットしました");
                    return true;
                }
                if (args[0].equals("setfood"))
                {
                    if (!sender.hasPermission("mspawn.op"))
                    {
                        sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
                        return true;
                    }
                    boolean isNumeric = args[1].matches("-?\\d+");
                    if (!isNumeric)
                    {
                        sender.sendMessage("§l[§fMan10Spawn§f§l]§c満腹度は整数にしてください");
                        return true;
                    }
                    int setfood = Integer.parseInt(args[1]);
                    if (setfood>20||setfood<0)
                    {
                        sender.sendMessage("§l[§fMan10Spawn§f§l]§c満腹度は0以上20以下の数字にしてください");
                        return true;
                    }
                    mspawn.getConfig().set("respawnfood",setfood);
                    mspawn.saveConfig();
                    respawnfood = setfood;
                    sender.sendMessage("§l[§fMan10Spawn§f§l]§eセットしました");
                    return true;
                }
                if (args[0].equals("setmessage"))
                {
                    if (!sender.hasPermission("mspawn.op"))
                    {
                        sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
                        return true;
                    }
                    if (args[1].length()>100)
                    {
                        sender.sendMessage("§l[§fMan10Spawn§f§l]§c文字数は100文字までにしてください");
                        return true;
                    }
                    String setmessage = args[1];
                    mspawn.getConfig().set("respawnmessage",setmessage);
                    mspawn.saveConfig();
                    respawnmessage = setmessage;
                    sender.sendMessage("§l[§fMan10Spawn§f§l]§eセットしました");
                    return true;
                }
                break;
            }
            case 3:
            {
                if (args[0].equals("exception"))
                {
                    if (args[1].equals("add"))
                    {
                        if (!sender.hasPermission("mspawn.op"))
                        {
                            sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
                            return true;
                        }
                        Player addplayer = Bukkit.getPlayerExact(args[2]);
                        if (addplayer == null)
                        {
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§cそのプレイヤーは存在しません");
                            return true;
                        }
                        addplayer = Bukkit.getPlayer(args[2]);
                        if (exceptionplayers.contains(addplayer.getUniqueId()))
                        {
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§cそのプレイヤーはすでに除外されています");
                        }
                        else
                        {
                            exceptionplayers.add(addplayer.getUniqueId());
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§e"+addplayer.getName()+"を除外します");
                            mspawn.getConfig().set("exceptionplayerlist",exceptionplayers);
                            saveConfig();
                        }
                        return true;
                    }
                    if (args[1].equals("delete"))
                    {
                        if (!sender.hasPermission("mspawn.op"))
                        {
                            sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
                            return true;
                        }
                        Player deleteplayer = Bukkit.getPlayerExact(args[2]);
                        if (deleteplayer == null)
                        {
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§cそのプレイヤーは存在しません");
                            return true;
                        }
                        if (exceptionplayers.contains(deleteplayer.getUniqueId()))
                        {
                            exceptionplayers.remove(deleteplayer.getUniqueId());
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§e"+deleteplayer.getName()+"を対象にします");
                            mspawn.getConfig().set("exceptionplayerlist",exceptionplayers);
                            saveConfig();
                        }
                        else
                        {
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§cそのプレイヤーは除外されていません");
                        }
                        return true;
                    }
                }
                if (args[0].equals("exceptionw"))
                {
                    if (args[1].equals("add"))
                    {
                        if (!sender.hasPermission("mspawn.op"))
                        {
                            sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
                            return true;
                        }
                        World addworld = Bukkit.getWorld(args[2]);
                        List<World> worlds = Bukkit.getWorlds();
                        if (!worlds.contains(addworld))
                        {
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§cそのワールドは存在しません");
                            return true;
                        }
                        if (exceptionworlds.contains(addworld))
                        {
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§cそのワールドはすでに除外されています");
                        }
                        else
                        {
                            exceptionworlds.add(addworld);
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§e"+addworld+"を対象にします");
                            mspawn.getConfig().set("exceptionworldlist",exceptionworlds);
                            saveConfig();
                        }
                        return true;
                    }
                    if (args[1].equals("delete"))
                    {
                        if (!sender.hasPermission("mspawn.op"))
                        {
                            sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
                            return true;
                        }
                        World deleteworld = Bukkit.getWorld(args[2]);
                        List<World> worlds = Bukkit.getWorlds();
                        if (!worlds.contains(deleteworld))
                        {
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§cそのワールドは存在しません");
                            return true;
                        }
                        if (exceptionworlds.contains(deleteworld))
                        {
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§cそのワールドはすでに除外されています");
                        }
                        else
                        {
                            exceptionworlds.remove(deleteworld);
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§e"+deleteworld+"を対象にします");
                            mspawn.getConfig().set("exceptionworldlist",exceptionworlds);
                            saveConfig();
                        }
                        return true;
                    }
                }
            }
            default:
            {
                if (sender.hasPermission("mspawn.op"))
                {
                    sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn set : spawn地点を現在地にセットします");
                    sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn reload : configをリロードします");
                    sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn sethealth : リスポーン時の体力を設定します");
                    sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn on : mspawnを有効化します");
                    sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn off : mspawnを無効化します");
                    sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn exception add [ユーザー名] : mspawnを無効化するプレイヤーを追加します");
                    sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn exception delete [ユーザー名] : mspawnを無効化するプレイヤーから除外します");
                    sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn exceptionw add [ワールド名] : mspawnを無効化するワールドを追加します");
                    sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn exceptionw delete [ワールド名] : mspawnを無効化するワールドから除外します");
                    sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn setfood : リスポーン時の満腹度を設定します");
                    sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn message : リスポーン時のメッセージを設定します");
                    sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn respawn [ユーザー名] : 特定のユーザーをリスポーンします");
                }
                sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn respawn : リスポーンします");
                return true;
            }
        }
        return true;
    }

    @EventHandler
    public void PlayerRespawnEvent(PlayerRespawnEvent event)
    {
        if (exceptionplayers.contains(event.getPlayer().getUniqueId()) || exceptionworlds.contains(event.getPlayer().getWorld()))
        {
            return;
        }
        Location respawnlocation = event.getRespawnLocation();
        respawnlocation.setX(respawnx);
        respawnlocation.setY(respawny);
        respawnlocation.setZ(respawnz);
        respawnlocation.setYaw(respawnyaw);
        respawnlocation.setPitch(respawnpitch);
        respawnlocation.setWorld(respawnworld);
        event.setRespawnLocation(respawnlocation);
        Bukkit.getScheduler().runTaskLater(this, new Runnable()
        {
            @Override
            public void run() {
                event.getPlayer().setHealth(respawnhealth);
                event.getPlayer().setFoodLevel(respawnfood);
                event.getPlayer().sendMessage(respawnmessage);
            }
        }, 1);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        if(command.getName().equalsIgnoreCase("mspawn"))
        {
            if (args.length == 1)
            {
                if (args[0].length() == 0)
                {
                    if (sender.hasPermission("mspawn.player")&&!sender.hasPermission("mspawn.op"))
                    {
                        return Collections.singletonList("respawn");
                    }
                    if (sender.hasPermission("mspawn.op"))
                    {
                        return Arrays.asList("exception","exceptionw","message","off","on","reload","respawn","set","setfood","sethealth");
                    }
                }
                else
                {
                    if (!(sender.hasPermission("mspawn.player"))&&!(sender.hasPermission("mspawn.op")))
                    {
                        return null;
                    }
                    if ("respawn".startsWith(args[0])&&"reload".startsWith(args[0]))
                    {
                        if (sender.hasPermission("mspawn.op"))
                        {
                            return Arrays.asList("reload","respawn");
                        }
                        else
                        {
                            return Collections.singletonList("respawn");
                        }
                    }
                    if ("respawn".startsWith(args[0]))
                    {
                        return Collections.singletonList("respawn");
                    }
                    if ("reload".startsWith(args[0]))
                    {
                        if (sender.hasPermission("mspawn.op"))
                        {
                            return Collections.singletonList("reload");
                        }
                    }
                    else if ("on".startsWith(args[0]) && "off".startsWith(args[0]))
                    {
                        return Arrays.asList("on","off");
                    }
                    else if("on".startsWith(args[0]))
                    {
                        return Collections.singletonList("on");
                    }
                    else if("off".startsWith(args[0]))
                    {
                        return Collections.singletonList("off");
                    }
                    else if ("exception".startsWith(args[0])&&"exceptionw".startsWith(args[0]))
                    {
                        return Arrays.asList("exception","exceptionw");
                    }
                    else if ("exceptionw".startsWith(args[0]))
                    {
                        return Collections.singletonList("exceptionw");
                    }
                    else if ("message".startsWith(args[0]))
                    {
                        return Collections.singletonList("message");
                    }
                    else if ("set".startsWith(args[0])&&"sethealth".startsWith(args[0])&&"setfood".startsWith(args[0]))
                    {
                        return Arrays.asList("set","sethealth","setfood");
                    }
                    else if ("sethealth".startsWith(args[0]))
                    {
                        return Collections.singletonList("sethealth");
                    }
                    else if ("setfood".startsWith(args[0]))
                    {
                        return Collections.singletonList("setfood");
                    }
                }
            }
            if (args.length == 2)
            {
                if (!sender.hasPermission("mspawn.op"))
                {
                    return null;
                }
                if (args[1].length() == 0)
                {
                    if (args[0].equals("message"))
                    {
                        return Collections.singletonList("<text>");
                    }
                    else if (args[0].equals("exceptionw"))
                    {
                        if ("add".startsWith(args[1]))
                        {
                            return Collections.singletonList("add");
                        }
                        else if ("delete".startsWith(args[1]))
                        {
                            return Collections.singletonList("delete");
                        }
                    }
                }
            }
            if (args.length == 3)
            {
                if (args[2].length() == 0)
                {
                    if (args[0].equals("exceptionw"))
                    {
                        ArrayList<String> list = new ArrayList<>();
                        for (World world : Bukkit.getWorlds())
                        {
                            list.add(world.getName());
                        }
                        return list;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void onDisable() {}
}
