package yusama125718_209282ihcuobust.man10respawnanchor;


import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
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
    public static List<Float> spawnyaw = new ArrayList<>();
    public static List<Double> spawnx = new ArrayList<>();
    public static List<Double> spawny = new ArrayList<>();
    public static List<Double> spawnz = new ArrayList<>();
    public static List<Float> spawnpitch = new ArrayList<>();
    public static List<String> movetargetworld = new ArrayList<>();
    public static List<Double> spawnyawd = new ArrayList<>();
    public static List<Double> spawnpitchd = new ArrayList<>();
    public static double respawnhealth;
    public static int respawnfood;
    public static Player respawnplayer;
    public static String respawnmessage;
    public static boolean system;
    public static boolean JoinSystem;
    public static boolean MoveSystem;
    public static boolean cpenalty;
    public static boolean dpenalty;
    public static List<UUID> exceptionplayers = new ArrayList<>();
    public static List<String> exceptionworlds = new ArrayList<>();

    @Override
    public void onEnable()
    {
        this.mspawn = this;
        saveDefaultConfig();
        respawnyawd.addAll(mspawn.getConfig().getDoubleList("respawnyaw"));
        double changeyawd;
        float changeyaw;
        for (int i = 0;i<(mspawn.getConfig().getDoubleList("respawnyaw")).size();i++)
        {
            changeyawd = respawnyawd.get(i);
            changeyaw = (float) (changeyawd);
            respawnyaw.add(changeyaw);
        }
        respawnx.addAll(mspawn.getConfig().getDoubleList("respawnx"));
        respawny.addAll(mspawn.getConfig().getDoubleList("respawny"));
        respawnz.addAll(mspawn.getConfig().getDoubleList("respawnz"));
        respawnpitchd.addAll(mspawn.getConfig().getDoubleList("respawnpitch"));
        double changepitchd;
        float changepitch;
        for (int i = 0;i<(mspawn.getConfig().getDoubleList("respawnpitch")).size();i++)
        {
            changepitchd = respawnpitchd.get(i);
            changepitch = (float) (changepitchd);
            respawnpitch.add(changepitch);
        }
        respawnworld.addAll(mspawn.getConfig().getStringList("respawnworld"));
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
        system = mspawn.getConfig().getBoolean("system");
        JoinSystem = mspawn.getConfig().getBoolean("joinsystem");
        cpenalty = mspawn.getConfig().getBoolean("cpenalty");
        dpenalty = mspawn.getConfig().getBoolean("dpenalty");
        try
        {
            for (int i = 0; i < mspawn.getConfig().getList("exceptionplayerlist").size(); i++)
            {
                exceptionplayers.add(UUID.fromString(mspawn.getConfig().getStringList("exceptionplayerlist").get(i)));
            }
        }
        catch (NullPointerException e)
        {
            System.out.println("§l[§fMan10Spawn§f§l]§r除外するプレイヤーのロードに失敗しました");
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
            java.lang.System.out.println("§l[§fMan10Spawn§f§l]§r除外するワールドのロードに失敗しました");
        }
        cpenalty = mspawn.getConfig().getBoolean("cpenalty");
        dpenalty = mspawn.getConfig().getBoolean("dpenalty");
        MoveSystem = mspawn.getConfig().getBoolean("movesystem");
        spawnyawd.addAll(mspawn.getConfig().getDoubleList("spawnyaw"));
        for (int i = 0;i<(mspawn.getConfig().getDoubleList("spawnyaw")).size();i++)
        {
            changeyawd = spawnyawd.get(i);
            changeyaw = (float) (changeyawd);
            spawnyaw.add(changeyaw);
        }
        spawnx.addAll(mspawn.getConfig().getDoubleList("spawnx"));
        spawny.addAll(mspawn.getConfig().getDoubleList("spawny"));
        spawnz.addAll(mspawn.getConfig().getDoubleList("spawnz"));
        spawnpitchd.addAll(mspawn.getConfig().getDoubleList("spawnpitch"));
        for (int i = 0;i<(mspawn.getConfig().getDoubleList("spawnpitch")).size();i++)
        {
            changepitchd = spawnpitchd.get(i);
            changepitch = (float) (changepitchd);
            spawnpitch.add(changepitch);
        }
        movetargetworld.addAll(mspawn.getConfig().getStringList("movetargetworld"));
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
                    system = true;
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
                    system = false;
                    mspawn.getConfig().set("system",false);
                    sender.sendMessage("§l[§fMan10Spawn§f§l]§eOFFにしました");
                    saveConfig();
                }
                if (args[0].equals("joinon"))
                {
                    if (!sender.hasPermission("mspawn.op"))
                    {
                        sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
                        return true;
                    }
                    JoinSystem = true;
                    mspawn.getConfig().set("joinsystem",true);
                    saveConfig();
                    sender.sendMessage("§l[§fMan10Spawn§f§l]§e入室時の設定をONにしました");
                }
                if (args[0].equals("joinoff"))
                {
                    if (!sender.hasPermission("mspawn.op"))
                    {
                        sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
                        return true;
                    }
                    JoinSystem = false;
                    mspawn.getConfig().set("joinsystem",false);
                    saveConfig();
                    sender.sendMessage("§l[§fMan10Spawn§f§l]§e入室時の設定をOFFにしました");
                }
                if (args[0].equals("moveon"))
                {
                    if (!sender.hasPermission("mspawn.op"))
                    {
                        sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
                        return true;
                    }
                    MoveSystem = true;
                    mspawn.getConfig().set("movesystem",true);
                    saveConfig();
                    sender.sendMessage("§l[§fMan10Spawn§f§l]§e入室時の設定をONにしました");
                }
                if (args[0].equals("moveoff"))
                {
                    if (!sender.hasPermission("mspawn.op"))
                    {
                        sender.sendMessage("§c[Man10Spawn]You don't have permissions!");
                        return true;
                    }
                    MoveSystem = false;
                    mspawn.getConfig().set("movesystem",false);
                    saveConfig();
                    sender.sendMessage("§l[§fMan10Spawn§f§l]§e入室時の設定をOFFにしました");
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
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn joinon : サーバー入室時のリスポーン地点をmspawnのリスポーン地点にします");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn joinoff : サーバー入室時のリスポーン地点をmspawnのリスポーン地点から解除します");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn moveon : ワールド移動時のリスポーン地点をmspawnのリスポーン地点にします");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn moveoff : ワールド移動時のリスポーン地点をmspawnのリスポーン地点から解除します");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn on : mspawnを有効化します");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn off : mspawnを無効化します");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn cpena [true/false] : コマンドでのリスポーン時のデスペナルティを設定します");
                        sender.sendMessage("§l[§fMan10Spawn§f§l] §7/mspawn dpena [true/false] : 死亡でのリスポーン時のデスペナルティを設定します");
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
                    respawnx.set(0,pLocation.getX());
                    respawny.set(0,pLocation.getY());
                    respawnz.set(0,pLocation.getZ());
                    respawnyaw.set(0,pLocation.getYaw());
                    respawnpitch.set(0,pLocation.getPitch());
                    respawnworld.set(0,pLocation.getWorld().getName());
                    targetworld.set(0,pLocation.getWorld().getName());
                    mspawn.getConfig().set("respawnx",respawnx);
                    mspawn.getConfig().set("respawny",respawny);
                    mspawn.getConfig().set("respawnz",respawnz);
                    mspawn.getConfig().set("respawnyaw",respawnyaw);
                    mspawn.getConfig().set("respawnpitch",respawnpitch);
                    mspawn.getConfig().set("respawnworld",respawnworld);
                    mspawn.getConfig().set("targetworld",targetworld);
                    mspawn.saveConfig();
                    sender.sendMessage("§l[§fMan10Spawn§f§l]§eセットしました");
                    return true;
                }
                if (args[0].equals("setmove"))
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
                    bbb: for (i=0;i<movetargetworld.size();i++)
                    {
                        if (Objects.requireNonNull(((Player) sender).getPlayer()).getLocation().getWorld().getName().equals(movetargetworld.get(i)))
                        {
                            break bbb;
                        }
                    }
                    if (i>movetargetworld.size()-1)
                    {
                        spawnx.add(pLocation.getX());
                        spawny.add(pLocation.getY());
                        spawnz.add(pLocation.getZ());
                        spawnyaw.add(pLocation.getYaw());
                        spawnpitch.add(pLocation.getPitch());
                        movetargetworld.add(pLocation.getWorld().getName());
                    }
                    else
                    {
                        spawnx.set(i,pLocation.getX());
                        spawny.set(i,pLocation.getY());
                        spawnz.set(i,pLocation.getZ());
                        spawnyaw.set(i,pLocation.getYaw());
                        spawnpitch.set(i,pLocation.getPitch());
                    }
                    mspawn.getConfig().set("spawnx",spawnx);
                    mspawn.getConfig().set("spawny",spawny);
                    mspawn.getConfig().set("spawnz",spawnz);
                    mspawn.getConfig().set("spawnyaw",spawnyaw);
                    mspawn.getConfig().set("spawnpitch",spawnpitch);
                    mspawn.getConfig().set("movetargetworld",movetargetworld);
                    mspawn.saveConfig();
                    sender.sendMessage("§l[§fMan10Spawn§f§l]§eセットしました");
                    return true;
                }
                if (args[0].equals("respawn"))
                {
                    if (!system)
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
                    aaa: for (i=0;i < targetworld.size();i++)
                    {
                        if (((respawnplayer.getLocation()).getWorld()).getName().equals(targetworld.get(i)))
                        {
                            break aaa;
                        }
                    }
                    if (i>targetworld.size() - 1)
                    {
                        i=0;
                    }
                    Location respawnlocation = respawnplayer.getLocation();
                    respawnlocation.setX(respawnx.get(i));
                    respawnlocation.setY(respawny.get(i));
                    respawnlocation.setZ(respawnz.get(i));
                    respawnlocation.setYaw(respawnyaw.get(i));
                    respawnlocation.setPitch(respawnpitch.get(i));
                    respawnlocation.setWorld(Bukkit.getWorld(respawnworld.get(i)));
                    respawnplayer.teleport(respawnlocation);
                    if (cpenalty)
                    {
                        respawnplayer.setHealth(respawnhealth);
                        respawnplayer.setFoodLevel(respawnfood);
                        respawnplayer.sendMessage(respawnmessage);
                    }
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
                if (args[0].equals("cpena"))
                {
                    if (args[1].equals("true"))
                    {
                        cpenalty = true;
                        mspawn.getConfig().set("cpenalty",cpenalty);
                        saveConfig();
                        sender.sendMessage("§l[§fMan10Spawn§f§l]§eコマンド使用時のペナルティをONにしました");
                    }
                    if (args[1].equals("false"))
                    {
                        cpenalty = false;
                        mspawn.getConfig().set("cpenalty",cpenalty);
                        saveConfig();
                        sender.sendMessage("§l[§fMan10Spawn§f§l]§eコマンド使用時のペナルティをOFFにしました");
                    }
                }
                if (args[0].equals("dpena"))
                {
                    if (args[1].equals("true"))
                    {
                        dpenalty = true;
                        mspawn.getConfig().set("dpenalty",dpenalty);
                        saveConfig();
                        sender.sendMessage("§l[§fMan10Spawn§f§l]§e死亡時のペナルティをONにしました");
                    }
                    if (args[1].equals("false"))
                    {
                        dpenalty = false;
                        mspawn.getConfig().set("dpenalty",dpenalty);
                        saveConfig();
                        sender.sendMessage("§l[§fMan10Spawn§f§l]§e死亡時のペナルティをOFFにしました");
                    }
                }
                if (args[0].equals("respawn"))
                {
                    if (!system)
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
                        mspawn.getConfig().set("respawnx",respawnx);
                        mspawn.getConfig().set("respawny",respawny);
                        mspawn.getConfig().set("respawnz",respawnz);
                        mspawn.getConfig().set("respawnyaw",respawnyaw);
                        mspawn.getConfig().set("respawnpitch",respawnpitch);
                        mspawn.getConfig().set("respawnworld",respawnworld);
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
                        mspawn.getConfig().set("respawnx",respawnx);
                        mspawn.getConfig().set("respawny",respawny);
                        mspawn.getConfig().set("respawnz",respawnz);
                        mspawn.getConfig().set("respawnyaw",respawnyaw);
                        mspawn.getConfig().set("respawnpitch",respawnpitch);
                        mspawn.getConfig().set("respawnworld",respawnworld);
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
                            List<String> addlist = mspawn.getConfig().getStringList("exceptionplayerlist");
                            addlist.add(addplayer.getUniqueId().toString());
                            mspawn.getConfig().set("exceptionplayerlist",addlist);
                            saveConfig();
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§e"+addplayer.getName()+"を除外します");
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
                            List<String> addlist = mspawn.getConfig().getStringList("exceptionplayerlist");
                            addlist.remove(deleteplayer.getUniqueId().toString());
                            mspawn.getConfig().set("exceptionplayerlist",addlist);
                            saveConfig();
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§e"+deleteplayer.getName()+"を対象にします");
                        }
                        else
                        {
                            sender.sendMessage("§l[§fMan10Spawn§f§l]§cそのプレイヤーは除外されていません");
                        }
                        return true;
                    }
                }
                List<String> worlds = new ArrayList<>();
                for (int i = 0; i<Bukkit.getWorlds().size(); i++)
                {
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
                        mspawn.getConfig().set("respawnx",respawnx);
                        mspawn.getConfig().set("respawny",respawny);
                        mspawn.getConfig().set("respawnz",respawnz);
                        mspawn.getConfig().set("respawnyaw",respawnyaw);
                        mspawn.getConfig().set("respawnpitch",respawnpitch);
                        mspawn.getConfig().set("respawnworld",respawnworld);
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
                sender.sendMessage("§l[§fMan10Spawn§f§l] §r/mspawn help でhelpを表示します");
                return true;
            }
        }
        return true;
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
                        return Arrays.asList("cpena","dpana","delete","exception","exceptionw","joinoff","joinon","message","moveoff","moveonn","off","on","reload","respawn","set","setfood","sethealth","setmove");
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
                    if ("cpena".startsWith(args[0]))
                    {
                        return Collections.singletonList("cpena");
                    }
                    if ("reload".startsWith(args[0]))
                    {
                        if (sender.hasPermission("mspawn.op"))
                        {
                            return Collections.singletonList("reload");
                        }
                    }
                    else if ("joinon".startsWith(args[0]) && "joinoff".startsWith(args[0]))
                    {
                        return Arrays.asList("joinon","joinoff");
                    }
                    else if("joinon".startsWith(args[0]))
                    {
                        return Collections.singletonList("joinon");
                    }
                    else if("joinoff".startsWith(args[0]))
                    {
                        return Collections.singletonList("joinoff");
                    }
                    else if ("moveon".startsWith(args[0]) && "moveoff".startsWith(args[0]))
                    {
                        return Arrays.asList("moveon","moveoff");
                    }
                    else if("moveon".startsWith(args[0]))
                    {
                        return Collections.singletonList("moveon");
                    }
                    else if("moveoff".startsWith(args[0]))
                    {
                        return Collections.singletonList("moveoff");
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
                    else if ("set".startsWith(args[0])&&"sethealth".startsWith(args[0])&&"setfood".startsWith(args[0])&&"setmove".startsWith(args[0]))
                    {
                        return Arrays.asList("set","sethealth","setfood","setmove");
                    }
                    else if ("sethealth".startsWith(args[0]))
                    {
                        return Collections.singletonList("sethealth");
                    }
                    else if ("setfood".startsWith(args[0]))
                    {
                        return Collections.singletonList("setfood");
                    }
                    else if ("setmove".startsWith(args[0]))
                    {
                        return Collections.singletonList("setmove");
                    }
                    else if ("delete".startsWith(args[0])&&"dpena".startsWith(args[0]))
                    {
                        return Arrays.asList("set","sethealth","setfood");
                    }
                    else if ("dpena".startsWith(args[0]))
                    {
                        return Collections.singletonList("dpena");
                    }
                    else if ("delete".startsWith(args[0]))
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
                    else if (args[0].equals("cpena")||args[0].equals("dpena"))
                    {
                        return Arrays.asList("true","false");
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
                else if (args[0].equals("cpena")||args[0].equals("dpena"))
                {
                    if ("true".startsWith(args[1]))
                    {
                        return Collections.singletonList("true");
                    }
                    else if ("false".startsWith(args[1]))
                    {
                        return Collections.singletonList("false");
                    }
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
