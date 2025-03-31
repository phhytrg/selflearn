import { useAuth } from '@/auth/hooks/useAuth';
import { UserRole } from '../app.enum';

export const useRoles = () => {
  const { user } = useAuth();
  const roles = user?.roles.map((i) => i.authority);

  return {
    roles,
    isAdmin: roles?.includes(UserRole.Admin),
    isReader: roles?.includes(UserRole.Reader),
  };
};
