#!/usr/bin/env bash

export USE_SSL=true

awslocal kinesis create-stream --shard-count 2 --stream-name test-fast-lane

awslocal kinesis create-stream --shard-count 2 --stream-name test-slow-lane
