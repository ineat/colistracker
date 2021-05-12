# ColisTracker UPS client

"""The Python implementation of the GRPC colistracker.Tracking client."""

from __future__ import print_function
import logging
import grpc

import colistracker_pb2
import colistracker_pb2_grpc

def run():
  # NOTE(gRPC Python Team): .close() is possible on a channel and should be
  # used in circumstances in which the with statement does not fit the needs
  # of the code.
  with grpc.insecure_channel('localhost:50051') as channel:
    stub = colistracker_pb2_grpc.TrackingStub(channel)
    response = stub.GetPacketStatus(colistracker_pb2.TrackingRequest(trackingNumber='1Z5338FF0107231059'))
    
    print("Tracker client received: " + response.message)
    for x in response.activities:
      print(x)

if __name__ == '__main__':
    logging.basicConfig()
    run()
