provider "google" {
  zone = "europe-west1-b"
  credentials = "./colistracker-terraform-keyfile.json"
}

resource "google_container_cluster" "colistracker" {
    project = "ineat-devops-colistracker-dev" 
    name = "gke-colistracker"
    initial_node_count = 1


    node_config {
        disk_size_gb = 10
        image_type = "COS"
        machine_type = "e2-micro"
        service_account = "colistracker@ineat-devops-colistracker-dev.iam.gserviceaccount.com"
    }

}