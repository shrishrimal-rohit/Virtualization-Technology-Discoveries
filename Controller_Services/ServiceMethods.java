package PerformanceMonitoring.Controller_Services;

import PerformanceMonitoring.AppController.BadRequestException;
import PerformanceMonitoring.Model.Host;
import PerformanceMonitoring.Model.Stats;
import PerformanceMonitoring.Model.VM;
import PerformanceMonitoring.Model.VMTask;
import com.vmware.vim25.mo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ServiceMethods {
    static final String SERVER_NAME = "130.65.159.14";
    static final String USER_NAME = "cmpe283_sec3_team6@vsphere.local";
    //static final String USER_NAME = "vsphere.local\\cmpe283_sec3_student";
    static final String PASSWORD = "cmpe283-cb86";
    //static final String PASSWORD = "cmpe283@sec3";
    private static final String HOSTNAME = "130.65.159.11";
    private static final int SELECTED_COUNTER_ID = 25; // Active (mem) in KB (absolute)
    public static final String COLLECTION_logs = "logs";

    @Autowired
    private MongoTemplate mongoTemplate;


    public Map<String, Object> getVmStats(String vm_name, String start_time,String end_time) throws BadRequestException
    {
        BasicQuery query_stats = new BasicQuery("{timestamp : { \"$gte\" : \""+start_time+"\" , \"$lte\" : \""+end_time+"\"} , vmName : \""+vm_name+"\"}");

        try {
            ArrayList<Stats> metrics = (ArrayList<Stats>) mongoTemplate.find(query_stats, Stats.class,COLLECTION_logs);
            Map<String, Object> metricMap = new HashMap<String, Object>();

            ArrayList<Integer> cpu_usage_list = new ArrayList<Integer>();
            ArrayList<Integer> memory_usage_list;
            memory_usage_list = new ArrayList<Integer>();
            ArrayList<Integer> net_usage_list;
            net_usage_list = new ArrayList<Integer>();
            ArrayList<Integer> disk_usage_list = new ArrayList<Integer>();

            for(Stats stats: metrics)
            {
                cpu_usage_list.add(stats.getCpuUsage());
                memory_usage_list.add(stats.getMemoryUsage());
                net_usage_list.add(stats.getNetUsage());
                disk_usage_list.add(stats.getDiskUsage());
            }

            metricMap.put("vmName", metrics.get(0).getVmName());
            metricMap.put("cpu.usage.average", cpu_usage_list);
            metricMap.put("memory.usage.average", memory_usage_list);
            metricMap.put("disk.usage.average", disk_usage_list);
            metricMap.put("net.usage.average", net_usage_list);

            return metricMap;
        }
        catch (Exception e)
        {
            throw new BadRequestException("Entity not found or exception" + e);
        }

    }


    public Stats getVM(String id)
    {
        Stats s = mongoTemplate.findById(id, Stats.class, COLLECTION_logs);
        return s;
    }

    public Host getHost(){
        Host host = new Host((long) 1, "test");
        return host;
    }

    public List<Host> getHosts() throws MalformedURLException, RemoteException {
        List<Host> hostList = new ArrayList<Host>();
        String url = "https://" + SERVER_NAME + "/sdk/vimService";
        ServiceInstance si = new ServiceInstance(new URL(url), USER_NAME, PASSWORD, true);
        Folder rootFolder = si.getRootFolder();
        ManagedEntity[] hosts = new InventoryNavigator(rootFolder).searchManagedEntities("HostSystem");
        int numberofHosts = 0;
        for(ManagedEntity me:hosts){
            Host host = new Host();
            host.setId((long) numberofHosts);
            System.out.println("IP Address: " + me.getName());
            host.setIpAddr(me.getName());
            System.out.println("Name: "+ me.getMOR().getVal());
            host.setName(me.getMOR().getVal());
            System.out.println("Product Full Name: " + si.getAboutInfo().getFullName());
            host.setProductFullName(si.getAboutInfo().getFullName());
            System.out.println("Overall Status: "+ me.getOverallStatus());
            host.setOverAllStatus(me.getOverallStatus().name());
            hostList.add(host);
            numberofHosts++;
        }
        System.out.println();

        /*Host host1 = new Host((long) 1, "test1");
        Host host2 = new Host((long) 2, "test2");
        hostList.add(host1);
        hostList.add(host2);*/
        return hostList;
    }

    public List<VM> getVMs() throws MalformedURLException, RemoteException {
        List<VM> vmList = new ArrayList<VM>();
        String url = "https://" + SERVER_NAME + "/sdk/vimService";
        ServiceInstance si = new ServiceInstance(new URL(url), USER_NAME, PASSWORD, true);
        Folder rootFolder = si.getRootFolder();
        ManagedEntity[] vms = new InventoryNavigator(rootFolder).searchManagedEntities("VirtualMachine");

        if(vms == null){
            System.out.println("VMS is null");
            return vmList;
        }
        else{
            for(ManagedEntity me: vms){
                VirtualMachine collegeVM = (VirtualMachine)me;
                VM modelVM = new VM();
                modelVM.setName(collegeVM.getName());
                modelVM.setGuestOS(collegeVM.getConfig().guestFullName);
                modelVM.setGuestState(collegeVM.getGuest().guestState);
                modelVM.setPowerState(collegeVM.getSummary().runtime.powerState.toString());
                //System.out.println("Host Name " + collegeVM.getRuntime().host.get_value());
                modelVM.setHostName(collegeVM.getRuntime().host.get_value());
                //System.out.println("Host Memory Usage " + collegeVM.getSummary().getQuickStats().getHostMemoryUsage());
                modelVM.setHostMemoryUsage(String.valueOf(collegeVM.getSummary().getQuickStats().getHostMemoryUsage()));
                //System.out.println("Guest Memory Usage " + collegeVM.getSummary().getQuickStats().getGuestMemoryUsage());
                modelVM.setGuestMemoryUsage(String.valueOf(collegeVM.getSummary().getQuickStats().getGuestMemoryUsage()));
                //System.out.println("Guest CPU Usage " + collegeVM.getSummary().getQuickStats().getOverallCpuUsage());
                modelVM.setGuestCPUUsage(String.valueOf(collegeVM.getSummary().getQuickStats().getOverallCpuUsage()));
                //System.out.println("Guest UpTime "+ collegeVM.getSummary().getQuickStats().getUptimeSeconds());
                modelVM.setGuestUpTime(String.valueOf(collegeVM.getSummary().getQuickStats().getUptimeSeconds()));
                vmList.add(modelVM);
            }
        }
        return vmList;
    }

    public VMTask startVM(String vmName) throws MalformedURLException, RemoteException {
        VMTask vmTask = new VMTask();
        vmTask.setVmName(vmName);
        vmTask.setVmTask("startVM");
        String url = "https://" + SERVER_NAME + "/sdk/vimService";
        ServiceInstance si = new ServiceInstance(new URL(url), USER_NAME, PASSWORD, true);
        Folder rootFolder = si.getRootFolder();
        VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", vmName);
        if(vm.getSummary().runtime.powerState.name().equals("poweredOff")){
            Task task = vm.powerOnVM_Task(null);
            //vm.powerOnVM_Task(null);
            if(task.waitForMe()==Task.SUCCESS){
                System.out.println("Power On VM: Status = " + Task.SUCCESS);
                vmTask.setVmTaskStatus("Success");
                return vmTask;
            }
        }
        vmTask.setVmTaskStatus("Failed");
        return vmTask;
    }

    public VMTask stopVM(String vmName) throws MalformedURLException, RemoteException {
        VMTask vmTask = new VMTask();
        vmTask.setVmName(vmName);
        vmTask.setVmTask("stopVM");
        System.out.println("Stop VM: " + vmName);
        String url = "https://" + SERVER_NAME + "/sdk/vimService";
        ServiceInstance si = new ServiceInstance(new URL(url), USER_NAME, PASSWORD, true);
        Folder rootFolder = si.getRootFolder();
        VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", vmName);
        if(vm == null){
            vmTask.setVmTaskStatus("VM not found");
            return vmTask;
        }
        if(vm.getSummary().runtime.powerState.name().equals("poweredOn")){
            Task task = vm.powerOffVM_Task();
            //vm.powerOffVM_Task();
            if(task.waitForMe()==Task.SUCCESS){
                System.out.println("Power Off VM: Status = " + Task.SUCCESS);
                vmTask.setVmTaskStatus("Success");
                return vmTask;
            }
        }
        vmTask.setVmTaskStatus("Failed");
        return vmTask;
    }

    public VMTask pauseVM(String vmName) throws MalformedURLException, RemoteException {
        VMTask vmTask = new VMTask();
        vmTask.setVmName(vmName);
        //vmTask.setVmTask("pauseVM");
        String url = "https://" + SERVER_NAME + "/sdk/vimService";
        ServiceInstance si = new ServiceInstance(new URL(url), USER_NAME, PASSWORD, true);
        Folder rootFolder = si.getRootFolder();
        VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", vmName);
        if(vm.getSummary().runtime.powerState.name().equals("poweredOn")){
            Task task = vm.suspendVM_Task();
            vm.suspendVM_Task();
            if(task.waitForMe()==Task.SUCCESS){
                System.out.println("Paused VM: Status = " + Task.SUCCESS);
                vmTask.setVmTaskStatus("Success");
                return vmTask;
            }
        }
        vmTask.setVmTaskStatus("Failed");
        return vmTask;
    }

    public String restartVM(String vmName) throws MalformedURLException, RemoteException {
        String url = "https://" + SERVER_NAME + "/sdk/vimService";
        ServiceInstance si = new ServiceInstance(new URL(url), USER_NAME, PASSWORD, true);
        Folder rootFolder = si.getRootFolder();
        VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", vmName);
        vm.getGuest().getToolsStatus();
        if(vm.getSummary().runtime.powerState.name().equals("poweredOn")){
            Task task = vm.resetVM_Task();
            vm.resetVM_Task();
            if(task.waitForMe()==Task.SUCCESS){
                System.out.println("Paused VM: Status = " + Task.SUCCESS);
                return "Success";
            }
        }
        return "Failed";
    }

}
