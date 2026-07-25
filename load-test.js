import http from 'k6/http';
import { check } from 'k6';

export const options = {
  vus: 100,
  duration: '2m',
};

const token = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidmFpc2huYXZiaGF2eWExNzAxQGdtYWlsLmNvbSIsImlhdCI6MTc4Mzc3NjE0MCwiZXhwIjoxNzgzNzc5NzQwfQ.8O9p7YOGXdkHZo8ebRjmAjQ30CeJoE2Ww072jGVYZdse0vuZBd1M2gqIh1jrvqyz2FOHGRCMzenjTcAs4nd5GQ';

export default function () {
  const res = http.get(
    'http://localhost:8080/health/user/profile',
    {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }
  );

  check(res, {
    'status is 200': (r) => r.status === 200,
  });
}