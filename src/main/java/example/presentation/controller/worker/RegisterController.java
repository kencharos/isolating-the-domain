package example.presentation.controller.worker;

import example.application.service.worker.WorkerRecordService;
import example.domain.model.worker.Name;
import example.domain.model.worker.WorkerIdentifier;
import example.presentation.view.NewWorker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("worker/register")
@SessionAttributes({"newWorker"})
class RegisterController {

    private static final String[] accept =
            {
                    "name.value",
                    "mailAddress.value",
                    "phoneNumber.value",
            };

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(accept);
    }

    WorkerRecordService workerRecordService;

    @GetMapping(value = "")
    String clearSessionAtStart(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "forward:/worker/register/input";
    }

    @GetMapping(value = "input")
    String showForm(Model model) {
        NewWorker newWorker = new NewWorker();
        model.addAttribute("newWorker", newWorker);
        return "worker/register/form";
    }

    @GetMapping(value = "input/again")
    String showFormToModify() {
        return "worker/register/form";
    }

    @PostMapping(value = "confirm")
    String validate(@Validated @ModelAttribute("newWorker") NewWorker newWorker,
                    BindingResult result) {
        if (result.hasErrors()) return "worker/register/form";

        return "worker/register/confirm";
    }

    @GetMapping(value = "register")
    String registerThenRedirectAndClearSession(
            @ModelAttribute("newWorker") NewWorker newWorker,
            SessionStatus status, RedirectAttributes attributes) {
        Name name = newWorker.name();
        WorkerIdentifier workerIdentifier = workerRecordService.prepareNewContract();
        workerRecordService.updateName(workerIdentifier, name);
        workerRecordService.updateMailAddress(workerIdentifier, newWorker.mailAddress());
        workerRecordService.updatePhoneNumber(workerIdentifier, newWorker.phoneNumber());
        status.setComplete();

        attributes.addAttribute("name", name);
        attributes.addAttribute("id", workerIdentifier);

        return "redirect:/worker/register/completed";
    }

    @GetMapping(value = "completed")
    String showResult(Model model,
                      @RequestParam("name") String name,
                      @RequestParam("id") String id) {
        model.addAttribute("name", name);
        model.addAttribute("id", id);
        return "worker/register/result";
    }

    RegisterController(WorkerRecordService workerRecordService) {
        this.workerRecordService = workerRecordService;
    }
}