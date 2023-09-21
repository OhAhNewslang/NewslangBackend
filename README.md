# Branching

## private branch push
### 개인 브랜치 수정 사항 반영
private branch -> pull -> 작업 -> add -> commit -> push

## main branch merge, push
### 개인 브랜치 수정 사항 메인 브랜치에 반영, 메인 브랜치 pull 반드시 진행(메인 브랜치 변경 사항 있을 수 있음)
-> checkout main -> pull -> merge private branch -> push

## merge private branch
### 메인 브랜치를 개인 브랜치에 반영 
-> checkout private branch -> merge main branch -> push

# Git
git clone url

git branch [private branch]

git checkout [private branch]

git pull

git add [files...]

git commit -m "commit message"

git push

git checkout main

git pull

git merge [private branch]

-> conflict : 문제 파일 수정 후 git add, git commit, git push

git checkout [private branch]

git merge main

git push