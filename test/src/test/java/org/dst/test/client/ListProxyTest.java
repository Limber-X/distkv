package org.dst.test.client;

import com.google.common.collect.ImmutableList;
import org.dst.client.DstClient;
import org.dst.common.exception.KeyNotFoundException;
import org.dst.test.supplier.BaseTestSupplier;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ListProxyTest extends BaseTestSupplier {

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testPutAndGet() {
    DstClient client = newDstClient();
    client.lists().put("k1", ImmutableList.of("v1", "v2", "v3"));
    Assert.assertEquals(ImmutableList.of("v1", "v2", "v3"),client.lists().get("k1"));
    //exception test
    client.lists().get("k2");
    client.disconnect();
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testDel() {
    DstClient client = newDstClient();
    client.lists().put("k1", ImmutableList.of("v1", "v2", "v3"));
    client.lists().del("k1");
    //exception test
    client.lists().get("k1");
    client.disconnect();
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testLPut() {
    DstClient client = newDstClient();
    client.lists().put("k1", ImmutableList.of("v1", "v2", "v3"));
    client.lists().lput("k1", ImmutableList.of("v4", "v5"));
    Assert.assertEquals(ImmutableList.of("v4", "v5","v1", "v2", "v3"),client.lists().get("k1"));
    //exception test
    client.lists().lput("k2", ImmutableList.of("v4", "v5"));
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testRPut() {
    DstClient client = newDstClient();
    client.lists().put("k1", ImmutableList.of("v1", "v2", "v3"));
    client.lists().rput("k1", ImmutableList.of("v4", "v5"));
    Assert.assertEquals(ImmutableList.of("v1", "v2", "v3","v4", "v5"),client.lists().get("k1"));
    //exception test
    client.lists().rput("k2", ImmutableList.of("v4", "v5"));
    client.disconnect();
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testLDel() {
    DstClient client = newDstClient();
    client.lists().put("k1", ImmutableList.of("v1", "v2", "v3", "v4"));
    client.lists().ldel("k1",2);
    Assert.assertEquals(ImmutableList.of("v3", "v4"),client.lists().get("k1"));
    //exception test
    client.lists().ldel("k2",1);
    client.disconnect();
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testRDel() {
    DstClient client = newDstClient();
    client.lists().put("k1", ImmutableList.of("v1", "v2", "v3", "v4"));
    client.lists().rdel("k1",2);
    Assert.assertEquals(ImmutableList.of("v1", "v2"),client.lists().get("k1"));
    //exception test
    client.lists().rdel("k2",1);
    client.disconnect();
  }


}
