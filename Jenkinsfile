pipeline{
    agent any
    stages{
        stage('Maven build'){
            steps{
                sh "mvn clean install"
            }
         }
        stage('build image'){
             steps{
                dir("client-management") {
                 sh "docker build -t localhost:5000/clientmanagement:${BUILD_NUMBER} ."
                 }
                 dir("user-authentication") {
                  sh "docker build -t localhost:5000/userauthentication:${BUILD_NUMBER} ."
                  }
                dir("institution-management") {
                 sh "docker build -t localhost:5000/institutionmanagement:${BUILD_NUMBER} ."
                 }
                dir("menu-management") {
                 sh "docker build -t localhost:5000/menumanagement:${BUILD_NUMBER} ."
                 }
                dir("profile-management") {
                 sh "docker build -t localhost:5000/profilemanagement:${BUILD_NUMBER} ."
                 }
                dir("role-management") {
                 sh "docker build -t localhost:5000/rolemanagement:${BUILD_NUMBER} ."
                 }
                dir("user-management") {
                 sh "docker build -t localhost:5000/usermanagement:${BUILD_NUMBER} ."
                 }
                dir("wallet-management") {
                 sh "docker build -t localhost:5000/walletmanagement:${BUILD_NUMBER} ."
                 }
              }
            }
        stage('push image to registry'){
             steps{
                    sh "docker push localhost:5000/clientmanagement:${BUILD_NUMBER} "
                    sh "docker push localhost:5000/userauthentication:${BUILD_NUMBER} "
                    sh "docker push localhost:5000/institutionmanagement:${BUILD_NUMBER} "
                    sh "docker push localhost:5000/menumanagement:${BUILD_NUMBER} "
                    sh "docker push localhost:5000/profilemanagement:${BUILD_NUMBER} "
                    sh "docker push localhost:5000/rolemanagement:${BUILD_NUMBER} "
                    sh "docker push localhost:5000/usermanagement:${BUILD_NUMBER} "
                    sh "docker push localhost:5000/walletmanagement:${BUILD_NUMBER} "
              }
            }
        stage('package chart'){
                steps{
                    sh "helm package client-management"
                    sh "helm upgrade clientmanagement client-management -i -n dev --set clientmanagement.image.tag=${BUILD_NUMBER} --set-string controller.config.proxy-body-size=50m --set-string controller.config.client-max-body-size=50m"
                    sh "helm package user-authentication"
                    sh "helm upgrade userauthentication user-authentication -i -n dev --set userauthentication.image.tag=${BUILD_NUMBER}"
                    sh "helm package institution-management"
                    sh "helm upgrade institutionmanagement institution-management -i -n dev --set institutionmanagement.image.tag=${BUILD_NUMBER}"
                    sh "helm package menu-management"
                    sh "helm upgrade menumanagement menu-management -i -n dev --set menumanagement.image.tag=${BUILD_NUMBER}"
                    sh "helm package profile-management"
                    sh "helm upgrade profilemanagement profile-management -i -n dev --set profilemanagement.image.tag=${BUILD_NUMBER}"
                    sh "helm package role-management"
                    sh "helm upgrade rolemanagement role-management -i -n dev --set rolemanagement.image.tag=${BUILD_NUMBER}"
                    sh "helm package user-management"
                    sh "helm upgrade usermanagement user-management -i -n dev --set usermanagement.image.tag=${BUILD_NUMBER}"
                    sh "helm package wallet-management"
                    sh "helm upgrade walletmanagement wallet-management -i -n dev --set walletmanagement.image.tag=${BUILD_NUMBER}"
                   }
              }
       }
}
