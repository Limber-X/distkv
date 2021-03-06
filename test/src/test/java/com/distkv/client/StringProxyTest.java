package com.distkv.client;

import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.supplier.BaseTestSupplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Set;

@Test(singleThreaded = true)
public class StringProxyTest extends BaseTestSupplier {

  @Test
  public void testPutAndGet() throws InvalidProtocolBufferException {
    DistkvClient client = newDistkvClient();
    client.strs().put("k1", "v1");
    Assert.assertEquals("v1", client.strs().get("k1"));
    client.disconnect();
  }

  @Test
  public void testKeyNotFoundWhenGetting() throws InvalidProtocolBufferException {
    DistkvClient client = newDistkvClient();
    try {
      client.strs().get("k1");
      Assert.fail("It shouldn't reach here.");
    } catch (KeyNotFoundException e) {
      Assert.assertTrue(true);
      client.disconnect();
    }
    client.disconnect();
  }

  @Test
  public void testAllTypeProxiesTogether() throws InvalidProtocolBufferException {
    DistkvClient client = newDistkvClient();

    // string
    client.strs().put("str_k1", "v1");
    Assert.assertEquals("v1", client.strs().get("str_k1"));

    // list
    client.lists().put("list_k1", ImmutableList.of("v1", "v2", "v3"));
    Assert.assertEquals(ImmutableList.of("v1", "v2", "v3"), client.lists().get("list_k1"));
    Assert.assertEquals(ImmutableList.of("v2", "v3"),
        client.lists().get("list_k1", 1, 3));

    // set
    Set<String> set = ImmutableSet.of("v1", "v2", "v3");
    client.sets().put("set_k1", set);
    Assert.assertEquals(set, client.sets().get("set_k1"));
    client.disconnect();
  }

  @Test
  public void testExpireStr() throws InterruptedException {
    DistkvClient client = newDistkvClient();
    client.strs().put("expired_k1", "v1");
    client.strs().expire("expired_k1", 1);
    Thread.sleep(3000);
    Assert.assertThrows(KeyNotFoundException.class, () -> client.strs().get("expired_k1"));
    client.disconnect();
  }

}
