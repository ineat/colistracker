import requests
import json

import settings

# class TrackingPayload(object):
# 	def __init__(self, jsonDict):
# 		self.__dict__ = jsonDict

def makeTackingRequest(trackingNumber):
	url = f"{settings.API_URL}/{trackingNumber}"

	headers = {
		'AccessLicenseNumber': settings.API_KEY,
		'Content-Type': 'application/json',
		'Accept': 'application/json'
	}

	response = requests.request("GET", url, headers=headers)
	jsonResponse = response.json()
	
	return jsonResponse['trackResponse']