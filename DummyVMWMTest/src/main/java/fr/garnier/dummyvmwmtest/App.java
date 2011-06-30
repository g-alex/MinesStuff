package fr.garnier.dummyvmwmtest;

import fr.garnier.btrpersistence.MachineConsumption;
import fr.garnier.btrpersistence.MachineType;
import fr.garnier.btrpersistence.Persist;
import fr.lelouet.server.perf.HVSnapshot;
import fr.lelouet.server.perf.snapshot.storage.FileStorage;
import fr.lelouet.server.perf.snapshot.storage.StringConverter;
import fr.lelouet.server.perf.vmware.DirectHostMonitor;
import fr.lelouet.server.perf.vmware.esxtop.Config;
import fr.lelouet.server.perf.vmware.esxtop.EsxTop;
import fr.lelouet.server.perf.vmware.esxtop.Translator;
import fr.lelouet.server.perf.vmware.esxtop.config.flags.Cpu;
import fr.lelouet.server.perf.vmware.execution.Common;
import fr.lelouet.tools.main.Args;
import fr.lelouet.tools.main.Args.KeyValArgs;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App {

    public static final String PROBINGPERIOD_S_KEY = "probing.periods";

    public static void main(String[] args) throws InterruptedException, Exception {
        args = new String[]{"host.ip=poweredge1.info.emn.fr"};
        String hostName = App.getName(args);
        String[] vms = new String[]{"vm-ubuntu-esx6"};
        KeyValArgs margs = Args.getArgs(args);
        FileStorage writer = new FileStorage();
        DirectHostMonitor con = null;

        long waitms = 20000;

        if (margs.props.containsKey(PROBINGPERIOD_S_KEY)) {
            try {
                waitms = 100L * Long.parseLong(margs.props.getProperty(PROBINGPERIOD_S_KEY));
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        EsxTop esx = Common.getEsxTopFromArgs(args);
        esx.setDurationS(10);
//        Config config = new Config();
//        config.add(Cpu.PCSTATETIMES);
//        esx.setConfig(config);
        con = new DirectHostMonitor(esx);
        esx.setTranslator(new Translator());
        System.out.println("Translator:" + esx.getTranslator());

        while (true) {
            long date = System.currentTimeMillis();
            con.asynchronousRetrieval();

            while (con.dirty()) {
                Thread.sleep(1);
            }

            App.persist(con.getLastSnapshot(), hostName, vms);

            long nextWait = waitms - (System.currentTimeMillis() - date);
            if (nextWait < 0) {
                nextWait = 0;
            }
            Thread.sleep(nextWait);
        }
    }

    private static void persist(HVSnapshot lastSnapshot, String hostName, String... vms) {
        Persist persist = new Persist();
        boolean isVM = false, isHost = true;
        String name = hostName;
        double ram = 0D;
        double cpu = 0D;
        double watt = -1D;
        for (String line : StringConverter.convertSnapshot(lastSnapshot)) {
            if (isHost) {
                if (line.startsWith("  ") && !line.startsWith("  *")) {
                    persist.save(new MachineConsumption(MachineType.SERVER,
                            System.currentTimeMillis(), name, watt, cpu, ram));
                    isHost = false;
                } else {
                    if (line.contains("PhysicalCpu(_Total).%UtilTime")) {
                        cpu = Double.valueOf(line.split(":")[1]);
                    } // if
                    if (line.contains("Memory.MachineMBytes")) {
                        ram += Double.valueOf(line.split(":")[1]);
                    } // if
                    if (line.contains("Memory.FreeMBytes")) {
                        ram -= Double.valueOf(line.split(":")[1]);
                    } // if
                    if (line.contains("Power.PowerUsageNowWatts")) {
                        watt = Double.valueOf(line.split(":")[1]);
                    } // if
                }
            }
            if (isVM) {
                if (!line.startsWith("   ")) {
                    persist.save(new MachineConsumption(MachineType.VM,
                            System.currentTimeMillis(), name, watt, cpu, ram));
                    isVM = false;
                } else {
                    if (line.contains("GroupCpu.%Run")) {
                        cpu = Double.valueOf(line.split(":")[1]);
                    } // if
                    if (line.contains("GroupMemory.CommitTargetMBytes")) {
                        ram += Double.valueOf(line.split(":")[1]);
                    } // if
                    if (line.contains("GroupMemory.CopyOnWriteHintMBytes")) {
                        ram -= Double.valueOf(line.split(":")[1]);
                    } // if
                    if (line.contains("")) {
                        System.out.println(line);
                    } // if
                } // if
            } // if
            for (String vm : vms) {
                if (line.contains(vm)) {
                    isVM = true;
                    name = vm;
                    cpu = 0D;
                    ram = 0D;
                    watt = -1D;
                    System.out.println(line);
                } // if
            } // for
        } // for
    }

    private static String getName(String[] args) throws Exception {
        for (String arg : args) {
            if (arg.contains("host.ip=")) {
                return arg.split("=")[1];
            }
        }
        throw new Exception("YEEHAR");
    }
}
