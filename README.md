## GitHub에서 프로젝트 클론
### git clone <GitHub_레포지토리_URL> <저장할경로>
#### .은 현재 경로
<br>

```git clone https://github.com/Class-WebDevelopmentWithJava/deployment-artifact.git /home/ec2-user/app/project```

<br>

```
#!/bin/bash

REPOSITORY=/home/ec2-user/app/
cd $REPOSITORY/project/

echo "> Git Pull"
git pull #|| { echo "Git Pull 실패!"; exit 1; }

chmod +x ./gradlew

echo "> 현재 구동중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -fl 'TableQ' | head -n 1 | awk '{print $1}')
echo "CURRENT_PID: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -15 $CURRENT_PID (SIGTERM)"
    sudo kill -15 $CURRENT_PID   # 정상 종료 신호(SIGTERM)
    sleep 5
		
		
    CURRENT_PID=$(pgrep -fl 'TableQ' | head -n 1 | awk '{print $1}')
    echo "CURRENT_PID: $CURRENT_PID"
    if [ -n "$CURRENT_PID" ]; then # 애플리케이션이 여전히 종료되지 않았다면 강제로 종료
        echo "> kill -9 $CURRENT_PID (SIGKILL)"
        sudo kill -9 $CURRENT_PID   # 강제 종료
        sleep 5
    fi
fi

echo "> 프로젝트 Clean > Build (테스트 제외) 시작"
./gradlew clean build --build-cache --parallel -x test || { echo "빌드 실패!"; exit 1; }

echo "> Build 파일 복사"
cp ./build/libs/*SNAPSHOT.jar $REPOSITORY/

echo "> 새 어플리케이션 배포"
JAR_NAME=$(ls $REPOSITORY/ |grep 'TableQ' | tail -n 1)
echo "> JAR Name: $JAR_NAME"

sudo nohup java -Xms128m -Xmx256m -jar $REPOSITORY/$JAR_NAME --server.port=8080 &
```
