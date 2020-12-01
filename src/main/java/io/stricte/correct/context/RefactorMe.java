package io.stricte.correct.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RefactorMe {

  public static void main(String[] args) {

    System.out.println("Improve / refactor / change these code snippets into something more meanigful");
    System.out.println("Add comments on why the changes were made\n");

    Asset a1 = new Asset("a1", 100);
    Asset a2 = new Asset("a2", 200);
    Asset a3 = new Asset("a3", 200);
    Asset a4 = new Asset("a4", 10000);

    ArrayList<Asset> assets1 = new ArrayList<>();
    assets1.add(a1);
    assets1.add(a2);
    assets1.add(a3);

    ArrayList<Asset> assets2 = new ArrayList<>();
    assets2.add(a2);
    assets2.add(a3);
    assets2.add(a4);

    Projects p1 = new Projects("p1", assets1);
    Projects p2 = new Projects("p2", assets2);

    ProjectPortfolio pp = new ProjectPortfolio();
    pp.addProject(p1);

    System.out.println("Test 1 for 100 - expected true : " + pp.ifVeryProfitableAssetsExist(50));
    System.out.println("Test 1 for 300 - expected false : " + pp.ifVeryProfitableAssetsExist(300));

    pp.addProject(p2);

    System.out.println("Test 2 for 150 - expected true : " + pp.ifVeryProfitableAssetsExist(150));
    System.out.println("Test 2 for 1000 - expected true : " + pp.ifVeryProfitableAssetsExist(1000));
    System.out.println("Test 2 for 1000000 - expected false : " + pp.ifVeryProfitableAssetsExist(1000000));

    pp.addManagersAndProjects("m1", Arrays.asList("id1", "id2"));
    System.out.println("Test 3 for 1 projects - expected false : " + (pp.managersAndProject.get("m1").size() == 1));
    System.out.println("Test 3 for 2 projects - expected true : " + (pp.managersAndProject.get("m1").size() == 2));
    pp.addManagersAndProjects("m1", Arrays.asList("id100", "id5"));
    System.out.println("Test 3 for 1 projects - expected false : " + (pp.managersAndProject.get("m1").size() == 1));
    System.out.println("Test 3 for 4 projects - expected true : " + (pp.managersAndProject.get("m1").size() == 4));
  }
}

class ProjectPortfolio {

  /*
  1. seems like there's implicit relation between Project and Manager although
  this code does everything to make unintuitive.
  2. should be final
  3. List specification buys nothing here.
  */
  private final Collection<Projects> projects = new ArrayList<>();
  /*
  no benefit of having red-black tree aka. sorted collection in here.
  in this particular context there's no advantage being taken of it.
  */
  public Map<String, List<String>> managersAndProject = new TreeMap<>();

  public void addProject(Projects p) {
    projects.add(p);
  }

  /*
  there's strict relation between projectId and Project but this method guarantees nothing
  about this relation
  */
  public void addManagersAndProjects(String manager, List<String> projectIds) {

    /*
    could and should be
    org.springframework.util.Assert#hasText(java.lang.String, java.lang.String)
    org.springframework.util.Assert#notEmpty(java.util.Collection<?>, java.lang.String)
    com.google.common.base.Preconditions#checkArgument(boolean)
    or any kind of other assertion on acceptable values
    */
    Assert.hasText(manager, "Manager cannot be blank");
    Assert.notEmpty(projectIds, "Project ids cannot be blank");

    for (String value : projectIds) {
      List<String> existingValues = managersAndProject.computeIfAbsent(manager, k -> new ArrayList<>());
      existingValues.add(value);
    }
  }

  /*
  unused
  */
  public void removeProject(final String projectId) {
    /*
    can be inlined to, although method ain't used at all. recommendation, remove it.
    */
    projects.removeIf(x -> projectId.equals(x.getProjectId()));
  }

  /*
  this method is of very little or no use at all in this particular context.
  the only information caller of this method is getting is boolean flag.
  I would recommend changing it to return Asset at least.
  */
  public boolean ifVeryProfitableAssetsExist(int profit) {
    /*
    can be done in single pass
    */
    for (Projects p : projects) {
      for (Asset asset : p.getAssets()) {
        if (asset.getProfit() > profit) {
          return true;
        }
      }
    }
    return false;
  }
}

/*
1. having setter instead of forcing assets as final field initialized via constructor
2. ambiguous class name, plural instead of singular
3. List is to specific in this context, Collection is enough
4. projectId getter
*/
class Projects {

  private final Collection<Asset> assets;
  private final String projectId;

  public Projects(String id, Collection<Asset> assets) {
    this.projectId = id;
    this.assets = Collections.unmodifiableCollection(assets);
  }

  public Collection<Asset> getAssets() {
    return assets;
  }

  public String getProjectId() {
    return projectId;
  }
}

/*
1. Asset is collection element, equals & hashCode should be added
2. toString is missing
*/
class Asset {

  // name ain't used anywhere can be removed or for readability became a part of toString methd
  private final String name;
  private final int profit;

  public Asset(String name, int profit) {
    /*
    could and should be
    org.springframework.util.Assert#isTrue(boolean, java.lang.String)
    com.google.common.base.Preconditions#checkArgument(boolean)
    or any kind of other assertion on acceptable values
    */
    Assert.hasText(name, "Name cannot be blank");
    Assert.isTrue(profit >= 0, "Profit cannot be less than zero");

    this.name = name;
    this.profit = profit;
  }

  public int getProfit() {
    return profit;
  }
}
