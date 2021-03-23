# ColisTracker : UPS tracker

This project runs a gRPC application

## Python (pipenv) install deps and activate virtual env

```
pipenv install
pipenv shell
```

## Start server

```
python tracker_server.py
```

## Generate python code

```bash
python -m grpc_tools.protoc -I ./protos --python_out=. --grpc_python_out=. ./protos/colistracker.proto
```