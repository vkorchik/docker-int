#!/usr/bin/env bash
set -e

ADVERTISED_HOSTNAME=$HOSTNAME

if [[ -n "$HOSTNAME_COMMAND" ]]; then
    export ADVERTISED_HOSTNAME=$(eval $HOSTNAME_COMMAND)
fi

echo ADVERTISED_HOSTNAME=$ADVERTISED_HOSTNAME

exec ./bin/docker-int
