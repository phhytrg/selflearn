import { http, HttpResponse } from 'msw';

export const authHandlers = [
  http.post('http://localhost:8080/api/v1/auth/login', async ({ request }) => {
    const { email, password } = (await request.json()) as {
      email: string;
      password: string;
    };

    if (email === '') {
      return new HttpResponse(null, {
        status: 400,
        statusText: 'Email is required',
      });
    }

    if (password === '') {
      return new HttpResponse(null, {
        status: 400,
        statusText: 'Password is required',
      });
    }

    // Check if email is valid
    if (!email.includes('@')) {
      return new HttpResponse(null, {
        status: 400,
        statusText: 'Invalid email',
      });
    }

    if (email === 'test@gmail.com' && password === 'Test@1234') {
      return HttpResponse.json({
        accessToken:
          'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmZmY4MGIwYy02MzQ3LTRiNGMtOWI2Mi03NjM0NzUxZDRkYTUiLCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiUkVBREVSIn1dLCJlbWFpbCI6InRlc3RAZ21haWwuY29tIiwiaWF0IjoxNzIwNDQ5OTcxLCJleHAiOjE3MjA1MzYzNzF9.zt16GV4TtM0vzrdF2V6vMQ50CTy4-TKrjgORrFzIm3g',
        refreshToken: '4fa30703-65e0-432a-9733-8678865eac2d',
      });
    }

    return new HttpResponse(null, { status: 401 });
  }),

  http.post('http://localhost:8080/api/v1/auth/signup', async ({ request }) => {
    const { email, password, confirmPassword } = (await request.json()) as {
      email: string;
      password: string;
      confirmPassword: string;
    };

    if (email === '') {
      return new HttpResponse(null, {
        status: 400,
        statusText: 'Email is required',
      });
    }

    if (password === '') {
      return new HttpResponse(null, {
        status: 400,
        statusText: 'Password is required',
      });
    }

    if (confirmPassword === '') {
      return new HttpResponse(null, {
        status: 400,
        statusText: 'Confirm password is required',
      });
    }

    if (password !== confirmPassword) {
      return new HttpResponse(null, {
        status: 400,
        statusText: 'Passwords do not match',
      });
    }

    if (email === 'test@gmail.com') {
      return new HttpResponse(null, {
        status: 400,
        statusText: 'Email already exists',
      });
    }

    return new HttpResponse(null, { status: 201 });
  }),
];
