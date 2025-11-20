package in.joyhong.resumebuilderapi.service;

import in.joyhong.resumebuilderapi.document.Resume;
import in.joyhong.resumebuilderapi.dto.AuthResponse;
import in.joyhong.resumebuilderapi.dto.CreateResumeRequest;
import in.joyhong.resumebuilderapi.repository.ResumeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final AuthService authService;

    public Resume createResume(CreateResumeRequest request, Object principalObject) {
        // Create resume object
        Resume newResume = new Resume();

        // Get the current profile
        AuthResponse response = authService.getProfile(principalObject);

        // Update the resume object
        newResume.setUserId(response.getId());
        newResume.setTitle(request.getTitle());

        // Set default data for resume
        setDefaultResumeData(newResume);

        // Save the resume data
        return resumeRepository.save(newResume);
    }

    private void setDefaultResumeData(Resume newResume) {
        newResume.setProfileInfo(new Resume.ProfileInfo());
        newResume.setContactInfo(new Resume.ContactInfo());
        newResume.setWorkExperience(new ArrayList<>());
        newResume.setEducation(new ArrayList<>());
        newResume.setSkills(new ArrayList<>());
        newResume.setProjects(new ArrayList<>());
        newResume.setCertifications(new ArrayList<>());
        newResume.setLanguages(new ArrayList<>());
        newResume.setInterests(new ArrayList<>());
    }

    public List<Resume> getUserResumes(Object principal) {
        // Get the current profile
        AuthResponse response = authService.getProfile(principal);

        // Call the repository finder method
        List<Resume> resumes = resumeRepository.findByUserIdOrderByUpdatedAtDesc(response.getId());

        // Return result
        return resumes;
    }

    public Resume getResumeById(String resumeId, Object principal) {
        // Get the current profile
        AuthResponse response = authService.getProfile(principal);

        // Call the repository finder method
        Resume existingResume = resumeRepository.findByUserIdAndId(response.getId(), resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found."));

        // Return result
        return existingResume;
    }

    public Resume updateResume(String resumeId, Resume updatedData, Object principal) {
        // Get the current profile 
        AuthResponse response = authService.getProfile(principal);

        // Call the repository finder method
        Resume existingResume = resumeRepository.findByUserIdAndId(response.getId(), resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        // Update new data
        existingResume.setTitle(updatedData.getTitle());
        existingResume.setThumbnailLink(updatedData.getThumbnailLink());
        existingResume.setTemplate(updatedData.getTemplate());
        existingResume.setProfileInfo(updatedData.getProfileInfo());
        existingResume.setContactInfo(updatedData.getContactInfo());
        existingResume.setWorkExperience(updatedData.getWorkExperience());
        existingResume.setEducation(updatedData.getEducation());
        existingResume.setSkills(updatedData.getSkills());
        existingResume.setProjects(updatedData.getProjects());
        existingResume.setCertifications(updatedData.getCertifications());
        existingResume.setLanguages(updatedData.getLanguages());
        existingResume.setInterests(updatedData.getInterests());

        // Update the details into database
        resumeRepository.save(existingResume);

        // Return result
        return existingResume;
    }

    public void deleteResume(String resumeId, Object principal) {
        // Get the current profile
        AuthResponse response = authService.getProfile(principal);

        // Call the repository finder method
        Resume existingResume = resumeRepository.findByUserIdAndId(response.getId(), resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found."));

        resumeRepository.delete(existingResume);
    }

}
