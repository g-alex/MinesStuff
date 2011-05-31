/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package btr.fr.garnier.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * MachineType tests
 *
 * @author agarnier
 */
public class MachineTypeTest {

    @Test
    public void toStringTest(){
        Assert.assertEquals(MachineType.SERVER.toString(), "Server");
        Assert.assertEquals(MachineType.VM.toString(), "VM");
    }
}
