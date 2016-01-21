package studentcompany.sportgest.Users;

import android.content.Context;

import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.daos.Pair;
import studentcompany.sportgest.daos.Role_DAO;
import studentcompany.sportgest.daos.Role_Permission_DAO;
import studentcompany.sportgest.daos.Permission_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Role;
import studentcompany.sportgest.domains.Permission;

public class RoleTestData {
    Permission_DAO permission_dao;
    Role_DAO role_dao;
    Role_Permission_DAO role_permission_dao;

    RoleTestData(Context context) {
        permission_dao = new Permission_DAO(context);
        role_dao = new Role_DAO(context);
        role_permission_dao = new Role_Permission_DAO(context);
        try {
            Permission perm1 = new Permission("View Users List");
            Permission perm2 = new Permission("Edit Users");
            perm1.setId(permission_dao.insert(perm1));
            perm2.setId(permission_dao.insert(perm2));

            Permission perm3 = new Permission("View Matches");
            perm3.setId(permission_dao.insert(perm3));
            Permission perm4 = new Permission("Edit Matches");
            perm4.setId(permission_dao.insert(perm3));
            permission_dao.insert(new Permission("View Players List"));
            permission_dao.insert(new Permission("Edit Players"));
            permission_dao.insert(new Permission("View Exercises"));
            permission_dao.insert(new Permission("Edit Exercises"));
            permission_dao.insert(new Permission("View Trainings"));
            permission_dao.insert(new Permission("Edit Trainings"));

            Role role1 = new Role("User Manager");
            Role role2 = new Role("Match Planner");
            role1.setId(role_dao.insert(role1));
            role2.setId(role_dao.insert(role2));
            role_dao.insert(new Role("Physio"));
            role_dao.insert(new Role("Player"));
            role_dao.insert(new Role("Coach"));
            role_dao.insert(new Role("Administrator"));
            role_dao.insert(new Role("Scout"));
            role_dao.insert(new Role("Chairman"));
            role_dao.insert(new Role("Supporter"));
            role_dao.insert(new Role("Waterboy"));
            role_permission_dao.insert(new Pair<Role, Permission>(role1,perm1));
            role_permission_dao.insert(new Pair<Role, Permission>(role1,perm2));
            role_permission_dao.insert(new Pair<Role, Permission>(role2,perm3));
            role_permission_dao.insert(new Pair<Role, Permission>(role2,perm4));
        }catch (GenericDAOException ex){
            ex.printStackTrace();
        }
    }
}

