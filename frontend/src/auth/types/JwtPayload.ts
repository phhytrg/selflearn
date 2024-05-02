export interface JwtPayload {
  sub: string;
  email: string;
  roles: {
    authority: string;
  }[];
  iat: number;
  exp: number;
}
