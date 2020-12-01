package io.stricte.correct.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import io.stricte.correct.context.RefactorMeRecommendation.Manager;
import io.stricte.correct.context.RefactorMeRecommendation.Project;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;

public class RefactorMeRecommendationTest {

  private Collection<RefactorMeRecommendation.Asset> assets1;

  private Collection<RefactorMeRecommendation.Asset> assets2;

  private RefactorMeRecommendation.ProjectPortfolio pp;

  @Before
  public void setUp() {

    RefactorMeRecommendation.Asset a1 = new RefactorMeRecommendation.Asset("a1", 100);
    RefactorMeRecommendation.Asset a2 = new RefactorMeRecommendation.Asset("a2", 200);
    RefactorMeRecommendation.Asset a3 = new RefactorMeRecommendation.Asset("a3", 200);
    RefactorMeRecommendation.Asset a4 = new RefactorMeRecommendation.Asset("a4", 10000);

    assets1 = new ArrayList<>();
    assets1.add(a1);
    assets1.add(a2);
    assets1.add(a3);

    assets2 = new ArrayList<>();
    assets2.add(a2);
    assets2.add(a3);
    assets2.add(a4);

    pp = new RefactorMeRecommendation.ProjectPortfolio();
  }

  @Test
  public void testVeryProfitableAssetsExistAfterProject1() {

    pp.addProject(new Project("p1", Manager.withId("m1"), assets1));
    assertTrue("Test 1 for 100 - expected true", pp.ifVeryProfitableAssetsExist(50));
    assertFalse("Test 1 for 300 - expected false", pp.ifVeryProfitableAssetsExist(300));
  }

  @Test
  public void testVeryProfitableAssetsExistAfterProject2() {

    pp.addProject(new Project("p2", Manager.withId("m2"), assets2));
    assertTrue("Test 2 for 150 - expected true", pp.ifVeryProfitableAssetsExist(150));
    assertTrue("Test 2 for 1000 - expected true", pp.ifVeryProfitableAssetsExist(1000));
    assertFalse("Test 2 for 1000000 - expected false", pp.ifVeryProfitableAssetsExist(1000000));
  }

  @Test
  public void testManagersProjectsWithinPortfolio() {

    // assets parameter are secondary for these assertions
    Manager johnDoe = Manager.withId("JohnDoe00012");
    pp.addProject(new Project("Advanced Telescope for High Energy Astrophysics", johnDoe, assets1));
    pp.addProject(new Project("Cassini–Huygens", johnDoe, assets1));
    assertNotEquals("Test 3 for 1 projects - expected false", 1, pp.managersProject(johnDoe).size()); // 1
    assertEquals("Test 3 for 2 projects - expected true", 2, pp.managersProject(johnDoe).size()); // 2

    pp.addProject(new Project("Global Positioning System", johnDoe, assets1));
    pp.addProject(new Project("International Space Station", johnDoe, assets1));
    assertNotEquals("Test 3 for 1 projects - expected false", 1, pp.managersProject(johnDoe).size()); // 1
    assertEquals("Test 3 for 4 projects - expected true", 4, pp.managersProject(johnDoe).size()); // 4
  }
}