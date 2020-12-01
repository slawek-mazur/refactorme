package io.stricte.correct.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

public class RefactorMeRecommendation {

  public static void main(String[] args) {

    System.out.println("Improve / refactor / change these code snippets into something more meanigful");
    System.out.println("Add comments on why the changes were made\n");

    /*
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

    Project p1 = new Project("p1", Manager.withId("m1"), assets1);
    Project p2 = new Project("p2", Manager.withId("m2"), assets2);

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
    */
  }


  static class ProjectPortfolio {

    private final Collection<Project> projects = new ArrayList<>();

    public void addProject(Project p) {
      projects.add(p);
    }

    public boolean ifVeryProfitableAssetsExist(int profit) {
      for (Project p : projects) {
        for (Asset asset : p.getAssets()) {
          if (asset.getProfit() > profit) {
            return true;
          }
        }
      }
      return false;
    }

    public Collection<Project> managersProject(Manager manager) {
      return projects.stream()
        .filter(p -> Objects.equals(manager, p.getManager()))
        .collect(Collectors.toSet());
    }
  }

  static class Manager {

    private final String id;

    private Manager(String id) {this.id = id;}

    public static Manager withId(String id) {
      return new Manager(id);
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
        return true;
      }
      if (object == null || getClass() != object.getClass()) {
        return false;
      }

      Manager manager = (Manager) object;

      return id.equals(manager.id);
    }

    @Override
    public int hashCode() {
      return id.hashCode();
    }
  }

  static class Project {

    // unused in this context
    private final String projectId;
    private final Manager manager;
    private final Collection<Asset> assets;

    public Project(String id, Manager manager, Collection<Asset> assets) {
      this.projectId = id;
      this.manager = manager;
      this.assets = Collections.unmodifiableCollection(assets);
    }

    public Collection<Asset> getAssets() {
      return assets;
    }

    public Manager getManager() {
      return manager;
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
        return true;
      }
      if (object == null || getClass() != object.getClass()) {
        return false;
      }

      Project project = (Project) object;

      if (!projectId.equals(project.projectId)) {
        return false;
      }
      if (!manager.equals(project.manager)) {
        return false;
      }
      return assets.equals(project.assets);
    }

    @Override
    public int hashCode() {
      int result = projectId.hashCode();
      result = 31 * result + manager.hashCode();
      result = 31 * result + assets.hashCode();
      return result;
    }
  }

  static class Asset {

    private final String name;
    private final int profit;

    public Asset(String name, int profit) {

      Assert.hasText(name, "Name cannot be blank");
      Assert.isTrue(profit >= 0, "Profit cannot be less than zero");

      this.name = name;
      this.profit = profit;
    }

    public int getProfit() {
      return profit;
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
        return true;
      }
      if (object == null || getClass() != object.getClass()) {
        return false;
      }

      Asset asset = (Asset) object;

      if (profit != asset.profit) {
        return false;
      }
      return name.equals(asset.name);
    }

    @Override
    public int hashCode() {
      int result = name.hashCode();
      result = 31 * result + profit;
      return result;
    }

    @Override
    public String toString() {
      return "Asset{" +
        "name='" + name + '\'' +
        ", profit=" + profit +
        '}';
    }
  }
}
