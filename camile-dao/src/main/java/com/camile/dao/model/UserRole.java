package com.camile.dao.model;

import java.io.Serializable;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table user_role
 *
 * @mbg.generated do_not_delete_during_merge Sat Nov 04 02:38:11 CST 2017
 */
public class UserRole implements Serializable {

    /**
     * Database Column Remarks:
     *   表id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_role.id
     *
     * @mbg.generated Sat Nov 04 02:38:11 CST 2017
     */
    private Long id;

    /**
     * Database Column Remarks:
     *   用户id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_role.user_id
     *
     * @mbg.generated Sat Nov 04 02:38:11 CST 2017
     */
    private Long userId;

    /**
     * Database Column Remarks:
     *   角色id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_role.role_id
     *
     * @mbg.generated Sat Nov 04 02:38:11 CST 2017
     */
    private Long roleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user_role
     *
     * @mbg.generated Sat Nov 04 02:38:11 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_role.id
     *
     * @return the value of user_role.id
     *
     * @mbg.generated Sat Nov 04 02:38:11 CST 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_role.id
     *
     * @param id the value for user_role.id
     *
     * @mbg.generated Sat Nov 04 02:38:11 CST 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_role.user_id
     *
     * @return the value of user_role.user_id
     *
     * @mbg.generated Sat Nov 04 02:38:11 CST 2017
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_role.user_id
     *
     * @param userId the value for user_role.user_id
     *
     * @mbg.generated Sat Nov 04 02:38:11 CST 2017
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_role.role_id
     *
     * @return the value of user_role.role_id
     *
     * @mbg.generated Sat Nov 04 02:38:11 CST 2017
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_role.role_id
     *
     * @param roleId the value for user_role.role_id
     *
     * @mbg.generated Sat Nov 04 02:38:11 CST 2017
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_role
     *
     * @mbg.generated Sat Nov 04 02:38:11 CST 2017
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", roleId=").append(roleId);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_role
     *
     * @mbg.generated Sat Nov 04 02:38:11 CST 2017
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UserRole other = (UserRole) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId())) && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId())) && (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_role
     *
     * @mbg.generated Sat Nov 04 02:38:11 CST 2017
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        return result;
    }
}
