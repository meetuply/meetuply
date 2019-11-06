export class User {
    userId: number;
    email: string;
    firstName: string;
    location: string;
    lastName: string;
    deactivated: boolean;
    password: string;
    confirmedPassword: string;
    description: string;
    photo: string;
    role: {
      roleName: string;
    }
}
