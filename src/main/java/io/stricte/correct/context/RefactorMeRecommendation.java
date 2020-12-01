package io.stricte.correct.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

public class RefactorMeRecommendation {

  public static void main(String[] args) { }

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
