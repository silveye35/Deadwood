import java.util.ArrayList;

public class Scene {
    int budget;
    private String imagename;
    private int[] area;
    Role[] roles;
    String sceneName;
    //reveal is 1 if it should show the card and reveal is 0 otherwise
    int reveal;
    int status;


    public Scene(int budget, Role[] roles, String sceneName, int status) {
        this.budget = budget;
        this.roles = roles;
        this.sceneName = sceneName;
        this.status = status;
        this.area = new int[4]; 

    }

    public void setBudget(int budget) {
        if (budget < 0) {
            throw new IllegalArgumentException("Budget can't be less than 0.");
        } else {
            this.budget = budget;
        }
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }
    
    public String getImage(){
      return imagename;
    }
    
    public void setImage(String image){
      imagename  = image;
    }
    
    public void setArea(int[] newarea){
      area = newarea;
    }

   public int[] getArea(){
      return area;
   }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public void setStatus(int status) {
        if (status == 0 || status == 1) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Status must be 1 (open) or 0 (closed)");
        }
    }

    public int getBudget() {
        return budget;
    }

    public Role[] getRoles() {
        return roles;
    }

    public String getSceneName() {
        return sceneName;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("+----");
        str.append(sceneName);
        str.append("----+");
        str.append("\nBudget: " + budget + "\n");
        for(Role r : roles){
            str.append(r);
        }
        str.append("+----");
        str.append(sceneName.replaceAll(".", "-"));
        str.append("----+");
        return str.toString();
    }
}
