# ColisTracker UPS

"""The Python implementation of the GRPC colistracker.Tracking server."""

from concurrent import futures
import logging
import grpc

import colistracker_pb2
import colistracker_pb2_grpc
import tracker

def buildPackageActivity(activityDict):
	loca = activityDict['location']['address']['city']
	stat = activityDict['status']['description']
	date = int(activityDict['date'])
	time = int(activityDict['time'])
	return colistracker_pb2.PackageActivity(location=loca, status=stat, date=date, time=time)

class Tracker(colistracker_pb2_grpc.TrackingServicer):

	def GetPacketStatus(self, request, context):
		## Make UPS API call
		trackingNumber = request.trackingNumber
		response = tracker.makeTackingRequest(trackingNumber)
		mssg = response['shipment'][0]['package'][0]['activity'][0]['status']['description']
		activities = []
		for item in response['shipment'][0]['package'][0]['activity']:
			activity = buildPackageActivity(item)
			activities.append(activity)

		return colistracker_pb2.TrackingResponse(message=f'Get status for packet {trackingNumber} : {mssg}', activities=activities)

def serve():
	server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
	colistracker_pb2_grpc.add_TrackingServicer_to_server(Tracker(), server)
	server.add_insecure_port('[::]:50051')
	server.start()
	server.wait_for_termination()

if __name__ == '__main__':
	logging.basicConfig()
	serve()
