#!/usr/bin/env bash

# 쉬고 있는 profile 찾기: prod1 사용 중 -> prod2 쉬는 중. prod2 사용 중 -> prod1 쉬는 중.
function find_idle_profile() {
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

  if [ "${RESPONSE_CODE}" -ge 400 ]; then # 400 보다 크면(즉, 40x/50x 에러 모두 포함)
    CURRENT_PROFILE=prod2
  else
    CURRENT_PROFILE=$(curl -s http://localhost/profile)
  fi

  if [ "${CURRENT_PROFILE}" == prod1 ]; then
    IDLE_PROFILE=prod2
  else
    IDLE_PROFILE=prod1
  fi

  echo "${IDLE_PROFILE}"
}

# 쉬고 있는 profile의 port 찾기
function find_idle_port() {
  IDLE_PROFILE=$(find_idle_profile)

  if [ "${IDLE_PROFILE}" == prod1 ]; then
    echo "8081"
  else
    echo "8082"
  fi
}
