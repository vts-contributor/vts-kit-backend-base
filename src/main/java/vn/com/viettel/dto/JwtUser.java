package vn.com.viettel.dto;

import lombok.Data;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.CollectionUtils;

@Data
public class JwtUser {
    private String sub;
    private String fullName;
    private String username;
    private Long userId;
    private List<String> roles;
    private List<String> permissions;
    private Long organizationId;

    private boolean checkContainsRole(String role) {
        return !CollectionUtils.isEmpty(this.roles) && this.roles.contains(role);
    }

    private boolean checkContainsPermission(String permission) {
        return !CollectionUtils.isEmpty(this.permissions) && this.permissions.contains(permission);
    }

    public void requireAnyRole(String... roles) {
        boolean flag = false;
        String[] var3 = roles;
        int var4 = roles.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String role = var3[var5];
            if (this.checkContainsRole(role)) {
                flag = true;
                break;
            }
        }

        if (!flag) {
            throw new AccessDeniedException("Unauthorized");
        }
    }

    public void requireAnyPermission(String... permissions) {
        boolean flag = false;
        String[] var3 = permissions;
        int var4 = permissions.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String permission = var3[var5];
            if (this.checkContainsPermission(permission)) {
                flag = true;
                break;
            }
        }

        if (!flag) {
            throw new AccessDeniedException("Unauthorized");
        }
    }

    public void requireAllPermission(String... permissions) {
        String[] var2 = permissions;
        int var3 = permissions.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String permission = var2[var4];
            if (!this.checkContainsPermission(permission)) {
                throw new AccessDeniedException("Unauthorized");
            }
        }

    }

    public JwtUser() {
    }

    public String getSub() {
        return this.sub;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getUsername() {
        return this.username;
    }

    public Long getUserId() {
        return this.userId;
    }

    public List<String> getRoles() {
        return this.roles;
    }

    public List<String> getPermissions() {
        return this.permissions;
    }

    public Long getOrganizationId() {
        return this.organizationId;
    }

    public void setSub(final String sub) {
        this.sub = sub;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public void setRoles(final List<String> roles) {
        this.roles = roles;
    }

    public void setPermissions(final List<String> permissions) {
        this.permissions = permissions;
    }

    public void setOrganizationId(final Long organizationId) {
        this.organizationId = organizationId;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof JwtUser)) {
            return false;
        } else {
            JwtUser other = (JwtUser)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label95: {
                    Object this$userId = this.getUserId();
                    Object other$userId = other.getUserId();
                    if (this$userId == null) {
                        if (other$userId == null) {
                            break label95;
                        }
                    } else if (this$userId.equals(other$userId)) {
                        break label95;
                    }

                    return false;
                }

                Object this$organizationId = this.getOrganizationId();
                Object other$organizationId = other.getOrganizationId();
                if (this$organizationId == null) {
                    if (other$organizationId != null) {
                        return false;
                    }
                } else if (!this$organizationId.equals(other$organizationId)) {
                    return false;
                }

                Object this$sub = this.getSub();
                Object other$sub = other.getSub();
                if (this$sub == null) {
                    if (other$sub != null) {
                        return false;
                    }
                } else if (!this$sub.equals(other$sub)) {
                    return false;
                }

                label74: {
                    Object this$fullName = this.getFullName();
                    Object other$fullName = other.getFullName();
                    if (this$fullName == null) {
                        if (other$fullName == null) {
                            break label74;
                        }
                    } else if (this$fullName.equals(other$fullName)) {
                        break label74;
                    }

                    return false;
                }

                label67: {
                    Object this$username = this.getUsername();
                    Object other$username = other.getUsername();
                    if (this$username == null) {
                        if (other$username == null) {
                            break label67;
                        }
                    } else if (this$username.equals(other$username)) {
                        break label67;
                    }

                    return false;
                }

                Object this$roles = this.getRoles();
                Object other$roles = other.getRoles();
                if (this$roles == null) {
                    if (other$roles != null) {
                        return false;
                    }
                } else if (!this$roles.equals(other$roles)) {
                    return false;
                }

                Object this$permissions = this.getPermissions();
                Object other$permissions = other.getPermissions();
                if (this$permissions == null) {
                    if (other$permissions != null) {
                        return false;
                    }
                } else if (!this$permissions.equals(other$permissions)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof JwtUser;
    }

    public int hashCode() {
        int result = 1;
        Object $userId = this.getUserId();
        result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
        Object $organizationId = this.getOrganizationId();
        result = result * 59 + ($organizationId == null ? 43 : $organizationId.hashCode());
        Object $sub = this.getSub();
        result = result * 59 + ($sub == null ? 43 : $sub.hashCode());
        Object $fullName = this.getFullName();
        result = result * 59 + ($fullName == null ? 43 : $fullName.hashCode());
        Object $username = this.getUsername();
        result = result * 59 + ($username == null ? 43 : $username.hashCode());
        Object $roles = this.getRoles();
        result = result * 59 + ($roles == null ? 43 : $roles.hashCode());
        Object $permissions = this.getPermissions();
        result = result * 59 + ($permissions == null ? 43 : $permissions.hashCode());
        return result;
    }

    public String toString() {
        return "JwtUser(sub=" + this.getSub() + ", fullName=" + this.getFullName() + ", username=" + this.getUsername() + ", userId=" + this.getUserId() + ", roles=" + this.getRoles() + ", permissions=" + this.getPermissions() + ", organizationId=" + this.getOrganizationId() + ")";
    }
}
