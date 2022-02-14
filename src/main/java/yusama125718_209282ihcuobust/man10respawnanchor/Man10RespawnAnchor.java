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
    public static JavaPlugin mspawn;
    public static List<Float> respawnyaw = new ArrayList<>();
    public static List<Double> respawnx = new ArrayList<>();
    public static List<Double> respawny = new ArrayList<>();
    public static List<Double> respawnz = new ArrayList<>();
    public static List<Float> respawnpitch = new ArrayList<>();
    public static List<String> respawnworld = new ArrayList<>();
    public static List<String> targetworld = new ArrayList<>();
    public static List<Double> respawnyawd = new ArrayList<>();
    public static List<Double> respawnpitchd = new ArrayList<>();
    public static double respawnhealth;
    public static int respawnfood;
    public static Player respawnplayer;
    public static String respawnmessage;
    public static boolean System;
    public static List<UUID> exceptionplayers = new ArrayList<>();
    public static List<String> exceptionworlds = new ArrayList<>();

    @Override
    public void onEnable()
    {
        this.mspawn = this;
        saveDefaultConfig();
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
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn set world : mspanwのそのワールド固有のspawn地点を現在地にセットします");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn set world [ワールド名] : mspanwの指定したワールド固有のspawn地点を現在地にセットします");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn delete world : mspanwのそのワールド固有のspawn地点を削除します");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn delete world [ワールド名] : mspanwの指定したワールド固有のspawn地点を削除します");
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
                    return true;
                }
                if (args[0].equals("set"))
                {
                    if (!(sender instanceof Player))
                    {
                        sender.sendMessage(("§c[Man10Spawn]Player以外は実行できません"));
                        return true;
                    }
                    if (!sender.hasPermission("mspawn.op"))
                    {
                        sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
                        return true;
                    }
                    Location pLocation = ((Player) sender).getLocation();
                    if (targetworld.size()==0)
                    {
                        respawnx.add(pLocation.getX());
                        respawny.add(pLocation.getY());
                        respawnz.add(pLocation.getZ());
                        respawnyaw.add(pLocation.getYaw());
                        respawnpitch.add(pLocation.getPitch());
                        respawnworld.add(pLocation.getWorld().getName());
                        targetworld.add(pLocation.getWorld().getName());
                    }
                    else
                    {
                        respawnx.set(0,pLocation.getX());
                        respawny.set(0,pLocation.getY());
                        respawnz.set(0,pLocation.getZ());
                        respawnyaw.set(0,pLocation.getYaw());
                        respawnpitch.set(0,pLocation.getPitch());
                        respawnworld.set(0,pLocation.getWorld().getName());
                        targetworld.set(0,pLocation.getWorld().getName());
                    }
                    mspawn.getConfig().set("spawnx",respawnx);
                    mspawn.getConfig().set("spawny",respawny);
                    mspawn.getConfig().set("spawnz",respawnz);
                    mspawn.getConfig().set("spawnyaw",respawnyaw);
                    mspawn.getConfig().set("spawnpitch",respawnpitch);
                    mspawn.getConfig().set("spawnworld",respawnworld);
                    mspawn.getConfig().set("targetworld",targetworld);
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
                    Reload reload = new Reload();
                    reload.start();
                    try
                    {
                        reload.join();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
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
                    Respawn respawn = new Respawn();
                    respawn.start();
                    try
                    {
                        respawn.join();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    sender.sendMessage("§l[§fMan10Spawn§f§l]§e"+respawnplayer.getName()+"をリスポーンしました");
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
                if (args[0].equals("set"))
                {
                    if (args[1].equals("world"))
                    {
                        if (!(sender instanceof Player))
                        {
                            sender.sendMessage(("§c[Man10Spawn]Player以外は実行できません"));
                            return true;
                        }
                        if (!sender.hasPermission("mspawn.op"))
                        {
                            sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
                            return true;
                        }
                        Location pLocation = ((Player) sender).getLocation();
                        int i = 0;
                        bbb: for (i=0;i<targetworld.size();i++)
                        {
                            if (Objects.requireNonNull(((Player) sender).getPlayer()).getLocation().getWorld().getName().equals(targetworld.get(i)))
                            {
                                break bbb;
                            }
                        }
                        if (i==0)
                        {
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§c"+Objects.requireNonNull(((Player) sender).getPlayer()).getLocation().getWorld().getName()+"はデフォルトのスポーン設定です");
                            return true;
                        }
                        if (i>targetworld.size()-1)
                        {
                            respawnx.add(pLocation.getX());
                            respawny.add(pLocation.getY());
                            respawnz.add(pLocation.getZ());
                            respawnyaw.add(pLocation.getYaw());
                            respawnpitch.add(pLocation.getPitch());
                            respawnworld.add(pLocation.getWorld().getName());
                            targetworld.add(pLocation.getWorld().getName());
                        }
                        else
                        {
                            respawnx.set(i,pLocation.getX());
                            respawny.set(i,pLocation.getY());
                            respawnz.set(i,pLocation.getZ());
                            respawnyaw.set(i,pLocation.getYaw());
                            respawnpitch.set(i,pLocation.getPitch());
                            respawnworld.set(i,pLocation.getWorld().getName());
                            targetworld.set(i,pLocation.getWorld().getName());
                        }
                        mspawn.getConfig().set("spawnx",respawnx);
                        mspawn.getConfig().set("spawny",respawny);
                        mspawn.getConfig().set("spawnz",respawnz);
                        mspawn.getConfig().set("spawnyaw",respawnyaw);
                        mspawn.getConfig().set("spawnpitch",respawnpitch);
                        mspawn.getConfig().set("spawnworld",respawnworld);
                        mspawn.getConfig().set("targetworld",targetworld);
                        mspawn.saveConfig();
                        sender.sendMessage("§l[§fMan10Spawn§f§l]§eセットしました");
                        return true;
                    }
                }
                if (args[0].equals("delete"))
                {
                    if (args[1].equals("world"))
                    {
                        if (!(sender instanceof Player))
                        {
                            sender.sendMessage(("§c[Man10Spawn]Player以外は実行できません"));
                            return true;
                        }
                        if (!sender.hasPermission("mspawn.op"))
                        {
                            sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
                            return true;
                        }
                        int i = 0;
                        ccc: for (i=0;i<targetworld.size();i++)
                        {
                            if (Objects.requireNonNull(((Player) sender).getPlayer()).getLocation().getWorld().getName().equals(targetworld.get(i)))
                            {
                                break ccc;
                            }
                        }
                        if (i>targetworld.size())
                        {
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§cこのワールドは登録されていません");
                            return true;
                        }
                        if (i==0)
                        {
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§c"+ Objects.requireNonNull(((Player) sender).getPlayer()).getLocation().getWorld().getName()+"はデフォルトのスポーン設定です");
                            return true;
                        }
                        respawnx.remove(i);
                        respawny.remove(i);
                        respawnz.remove(i);
                        respawnyaw.remove(i);
                        respawnpitch.remove(i);
                        respawnworld.remove(i);
                        targetworld.remove(i);
                        mspawn.getConfig().set("spawnx",respawnx);
                        mspawn.getConfig().set("spawny",respawny);
                        mspawn.getConfig().set("spawnz",respawnz);
                        mspawn.getConfig().set("spawnyaw",respawnyaw);
                        mspawn.getConfig().set("spawnpitch",respawnpitch);
                        mspawn.getConfig().set("spawnworld",respawnworld);
                        mspawn.getConfig().set("targetworld",targetworld);
                        mspawn.saveConfig();
                        sender.sendMessage("§l[§fMan10Spawn§f§l]§e削除しました");
                        return true;
                    }
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
                List<String> worlds = new ArrayList<>();
                java.lang.System.out.println("a");
                for (int i = 0; i<Bukkit.getWorlds().size(); i++)
                {
                    java.lang.System.out.println(i);
                    worlds.add(Bukkit.getWorlds().get(i).getName());
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
                        String addworld = args[2];
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
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§e"+addworld+"を除外します");
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
                        String deleteworld = args[2];
                        if (!worlds.contains(deleteworld))
                        {
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§cそのワールドは存在しません");
                            return true;
                        }
                        if (!exceptionworlds.contains(deleteworld))
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
                if (args[0].equals("set"))
                {
                    if (args[1].equals("world"))
                    {
                        if (!(sender instanceof Player))
                        {
                            sender.sendMessage(("§c[Man10Spawn]Player以外は実行できません"));
                            return true;
                        }
                        if (!sender.hasPermission("mspawn.op"))
                        {
                            sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
                            return true;
                        }
                        String addworld = args[2];
                        if (!worlds.contains(addworld))
                        {
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§cそのワールドは存在しません");
                            return true;
                        }
                        Location pLocation = ((Player) sender).getLocation();
                        int i = 0;
                        bbb: for (i=0;i<targetworld.size();i++)
                        {
                            if (addworld.equals(targetworld.get(i)))
                            {
                                break bbb;
                            }
                        }
                        if (i==0)
                        {
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§c"+addworld+"はデフォルトのスポーン設定です");
                            return true;
                        }
                        if (i>targetworld.size()-1)
                        {
                            respawnx.add(pLocation.getX());
                            respawny.add(pLocation.getY());
                            respawnz.add(pLocation.getZ());
                            respawnyaw.add(pLocation.getYaw());
                            respawnpitch.add(pLocation.getPitch());
                            respawnworld.add(pLocation.getWorld().getName());
                            targetworld.add(addworld);
                        }
                        else
                        {
                            respawnx.set(i,pLocation.getX());
                            respawny.set(i,pLocation.getY());
                            respawnz.set(i,pLocation.getZ());
                            respawnyaw.set(i,pLocation.getYaw());
                            respawnpitch.set(i,pLocation.getPitch());
                            respawnworld.set(i,pLocation.getWorld().getName());
                            targetworld.set(i,addworld);
                        }
                        mspawn.getConfig().set("spawnx",respawnx);
                        mspawn.getConfig().set("spawny",respawny);
                        mspawn.getConfig().set("spawnz",respawnz);
                        mspawn.getConfig().set("spawnyaw",respawnyaw);
                        mspawn.getConfig().set("spawnpitch",respawnpitch);
                        mspawn.getConfig().set("spawnworld",respawnworld);
                        mspawn.getConfig().set("targetworld",targetworld);
                        mspawn.saveConfig();
                        sender.sendMessage("§l[§fMan10Spawn§f§l]§eセットしました");
                        return true;
                    }
                }
                if (args[0].equals("delete"))
                {
                    if (args[1].equals("world"))
                    {
                        if (!(sender instanceof Player))
                        {
                            sender.sendMessage(("§c[Man10Spawn]Player以外は実行できません"));
                            return true;
                        }
                        if (!sender.hasPermission("mspawn.op"))
                        {
                            sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
                            return true;
                        }
                        String deleteworld = args[2];
                        if (!worlds.contains(deleteworld))
                        {
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§cそのワールドは存在しません");
                            return true;
                        }
                        int i = 0;
                        ccc: for (i=0;i<targetworld.size();i++)
                        {
                            if (deleteworld.equals(targetworld.get(i)))
                            {
                                break ccc;
                            }
                        }
                        if (i>targetworld.size())
                        {
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§cこのワールドは登録されていません");
                            return true;
                        }
                        if (i==0)
                        {
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§c"+deleteworld+"はデフォルトのスポーン設定です");
                            return true;
                        }
                        respawnx.remove(i);
                        respawny.remove(i);
                        respawnz.remove(i);
                        respawnyaw.remove(i);
                        respawnpitch.remove(i);
                        respawnworld.remove(i);
                        targetworld.remove(i);
                        mspawn.getConfig().set("spawnx",respawnx);
                        mspawn.getConfig().set("spawny",respawny);
                        mspawn.getConfig().set("spawnz",respawnz);
                        mspawn.getConfig().set("spawnyaw",respawnyaw);
                        mspawn.getConfig().set("spawnpitch",respawnpitch);
                        mspawn.getConfig().set("spawnworld",respawnworld);
                        mspawn.getConfig().set("targetworld",targetworld);
                        mspawn.saveConfig();
                        sender.sendMessage("§l[§fMan10Spawn§f§l]§e"+deleteworld+"の設定を削除しました");
                        return true;
                    }
                }
            }
            default:
            {
                if (sender.hasPermission("mspawn.op"))
                {
                    sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn set : mspanwのデフォルトのspawn地点を現在地にセットします");
                    sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn set world : mspanwのそのワールド固有のspawn地点を現在地にセットします");
                    sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn set world [ワールド名] : mspanwの指定したワールド固有のspawn地点を現在地にセットします");
                    sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn delete world : mspanwのそのワールド固有のspawn地点を削除します");
                    sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn delete world [ワールド名] : mspanwの指定したワールド固有のspawn地点を削除します");
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
                return true;
            }
        }
        return true;
    }

    @EventHandler
    public void PlayerRespawnEvent(PlayerRespawnEvent event)
    {
        if (exceptionplayers.contains(event.getPlayer().getUniqueId()) || exceptionworlds.contains(event.getPlayer().getWorld().getName()) || !System)
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
        if (i>=targetworld.size())
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
                        return Arrays.asList("delete","exception","exceptionw","message","off","on","reload","respawn","set","setfood","sethealth");
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
                    if ("delete".startsWith(args[0]))
                    {
                        return Collections.singletonList("delete");
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
                    else if (args[0].equals("exceptionw")||args[0].equals("exception"))
                    {
                        return Arrays.asList("add","delete");
                    }
                    else if (args[0].equals("set")||args[0].equals("delete"))
                    {
                        return Collections.singletonList("world");
                    }
                }
                else if (args[0].equals("exceptionw")||args[0].equals("exception"))
                {
                    if ("add".startsWith(args[1])&&"delete".startsWith(args[1]))
                    {
                        return Arrays.asList("add","delete");
                    }
                    else if ("add".startsWith(args[1]))
                    {
                        return Collections.singletonList("add");
                    }
                    else if ("delete".startsWith(args[1]))
                    {
                        return Collections.singletonList("delete");
                    }
                }
                else if (args[0].equals("set"))
                {
                    return Collections.singletonList("world");
                }
            }
            if (args.length == 3)
            {
                if (args[2].length() == 0)
                {
                    ArrayList<String> list = new ArrayList<>();
                    for (World world : Bukkit.getWorlds())
                    {
                        list.add(world.getName());
                    }
                    if (args[0].equals("exceptionw"))
                    {
                        return list;
                    }
                    else if (args[0].equals("set")||args[0].equals("delete"))
                    {
                        if (args[1].equals("world"))
                        {
                            return list;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void onDisable() {}
}
