# Branching

### private branch push
private branch -> pull -> 작업 -> add -> commit -> push

### main branch merge, push
-> checkout main -> pull -> merge private branch -> push

### merge private branch
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