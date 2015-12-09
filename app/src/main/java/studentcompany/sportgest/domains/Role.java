package studentcompany.sportgest.domains;
//TODO all

import java.util.List;

public class Role extends DomainPojo {

    private long id = -1;
    private String name = null;
    private List<Permission> permissionList = null;

    public Role(long id, String name, List<Permission> permissionList) {
        this.id = id;
        this.name = name;
        this.permissionList = permissionList;
    }
    public Role(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role(String name, List<Permission> permissionList) {
        this.name = name;
        this.permissionList = permissionList;
    }


    public Role(String name) {
        this.name = name;
    }



    @Override
    public long getId() { return this.id; }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }


    // Extra Methods

    public void addPermission(Permission permission){
        if(permission!=null)
            this.permissionList.add(permission);
    }

    public boolean hasPermission(Permission permission){
        if(permission!=null)
            return this.permissionList.contains(permission);
        else
            return false;
    }

    public boolean hasPermission(long id){
        for(Permission p : permissionList)
            if(p.getId() == id)
                return true;

        return false;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (permissionList != null ? permissionList.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (id != role.id) return false;
        return !(name != null ? !name.equals(role.name) : role.name != null);

    }

    @Override
    public String toString() {
        return name;
    }
}
