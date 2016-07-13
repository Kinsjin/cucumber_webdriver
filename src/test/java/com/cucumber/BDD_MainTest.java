package com.cucumber;

import com.cucumber.step.BDD_Main;
import org.junit.Test;

/**
 * Created by dingfan on 2016/7/13.
 */
public class BDD_MainTest {
    @Test
    public void test16() {
        String[] argv = new String[2];
        argv[0] = "features\\Infra\\10.ProcessChain\\11.FixIncompleteHost\\QCTP1H45861_62_64\\QCTP1H45861_62_64_FixIncompleteHost.feature";
        argv[1] = "UCMDB_INSTANCE.json";
        try {
            BDD_Main.main(argv);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Test
    public void test_stable_1() {
        String bddFile = "features\\Inventory\\08.InventoryDiscoveryWorkflow\\06.UpgradeScanner\\UploadScannerConfigurationFile\\QCTP1H40917_UploadConfFileToNonExistFolderOnRemote.feature";
//        BDD_Main.runBDDFromFile(bddFile, "SGDLITVM0428.json");
    }

}
